package com.byebyechallan.auth.controller;

import com.byebyechallan.auth.dao.AuthResponse;
import com.byebyechallan.auth.dao.LoginRequest;
import com.byebyechallan.auth.dao.RegisterRequest;
import com.byebyechallan.auth.entity.CoreUserMEntity;
import com.byebyechallan.auth.entity.RefreshTokenEntity;
import com.byebyechallan.auth.dao.TokenRefreshRequest;
import com.byebyechallan.auth.enums.Role;
import com.byebyechallan.auth.repository.UserRepository;
import com.byebyechallan.auth.service.JwtService;
import com.byebyechallan.auth.service.RefreshTokenService;
import com.byebyechallan.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationManager authManager;
  private final UserService userService;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public AuthenticationController(AuthenticationManager authManager,
      UserService userService, JwtService jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.authManager = authManager;
    this.userService = userService;
    this.jwtService = jwtService;
    this.refreshTokenService = refreshTokenService;
    this.passwordEncoder=passwordEncoder;
    this.userRepository=userRepository;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request){

    if(userService.loadUserByUsername(request.getEmail())!=null){
      return ResponseEntity.badRequest().body("User already exists");
    }
    CoreUserMEntity user = CoreUserMEntity.builder()
        .name(request.getName())
        .email(request.getEmail())
        .mobile(request.getMobileNo())
        .role(Role.USER)
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    userRepository.save(user);
    return ResponseEntity.ok().body("User registered successfully");
  }

  @PostMapping("/login")
  public AuthResponse authenticate(@RequestBody LoginRequest request) {
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
    String jwtToken = jwtService.generateToken(userDetails);
    RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
    return AuthResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken.getToken())
        .build();
  }

  @PutMapping("/refresh-token")
  public AuthResponse refresh(@RequestBody TokenRefreshRequest request) {
    String refreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(refreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshTokenEntity::getUser)
        .map(user -> {
          UserDetails userEntity = userService.loadUserByUsername(user.getEmail());
          String newAccessToken = jwtService.generateToken(userEntity);
          return AuthResponse.builder()
              .token(newAccessToken)
              .refreshToken(refreshToken)
              .build();
        }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));

  }


}
