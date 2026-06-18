package com.byebyechallan.auth.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;

  public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    try {
      final String authHeader = request.getHeader("Authorization");
      final String jwt;
      final String username;

      if (null == authHeader || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }

      jwt = authHeader.substring(7);
      
      try {
        username = jwtService.extractEmail(jwt);
      } catch (Exception e) {
        log.error("Error extracting email from token: {}", e.getMessage());
        filterChain.doFilter(request, response);
        return;
      }

      if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
        try {
          UserDetails userDetails = this.userService.loadUserByUsername(username);

          if (jwtService.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.debug("JWT Token validated for user: {}", username);
          } else {
            log.warn("JWT Token validation failed for user: {}", username);
          }
        } catch (Exception e) {
          log.error("Error loading or validating user: {} - {}", username, e.getMessage(), e);
        }
      }
    } catch (Exception e) {
      log.error("Unexpected error in JWT authentication filter", e);
    }
    filterChain.doFilter(request, response);
  }
}
