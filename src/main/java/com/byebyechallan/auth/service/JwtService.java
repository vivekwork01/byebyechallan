package com.byebyechallan.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String SECRET_KEY = "3cfa76ef14937c1c0eerthgbs345yh12erfee66a9dcf108872b89c7482f3ef24";
  private final int EXPIRATION_TIME_MS = 1000 * 60 * 15;

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", userDetails.getAuthorities());
    return generateToken(claims, userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(EXPIRATION_TIME_MS))
        .signWith(getSigningKey())
        .compact();
  }

  public String extractEmail(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  private Key getSigningKey() {
    return Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
    final Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
        .parseClaimsJwt(token).getBody();
    return claimResolver.apply(claims);
  }


  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String email = extractEmail(token);
    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaims(token, Claims::getExpiration);
  }
}
