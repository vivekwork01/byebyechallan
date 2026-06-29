package com.byebyechallan.auth.controller;

import com.byebyechallan.auth.dto.AuthResponse;
import com.byebyechallan.auth.dto.LoginRequest;
import com.byebyechallan.auth.dto.RegisterRequest;
import com.byebyechallan.auth.entity.CoreUserMEntity;
import com.byebyechallan.auth.entity.RefreshTokenEntity;
import com.byebyechallan.auth.dto.TokenRefreshRequest;
import com.byebyechallan.auth.enums.Role;
import com.byebyechallan.auth.repository.UserRepository;
import com.byebyechallan.auth.service.JwtService;
import com.byebyechallan.auth.service.RefreshTokenService;
import com.byebyechallan.auth.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Manager", description = "Endpoints for user authentication and registration")
@Slf4j
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
   @Operation(summary = "Register a New User", description = "Registers a new user with the provided details. Returns a success message if registration is successful, or an error message if the user already exists.")
   public ResponseEntity<?> register(@RequestBody RegisterRequest request){

     log.info("Registering user with email: {}", request.getEmail());
     try {
       userService.loadUserByUsername(request.getEmail());
       // If we reach here, user exists
       return ResponseEntity.badRequest().body("User already exists");
     } catch (UsernameNotFoundException e) {
       // User doesn't exist, proceed with registration
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
   }

  @PostMapping("/login")
  @Operation(summary = "User Login", description = "Login user with given details")
  public AuthResponse authenticate(@RequestBody LoginRequest request) {
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
    CoreUserMEntity user = userRepository.findByEmailAndIsDeleted(request.getUsername(), false)
        .orElseThrow(() -> new UsernameNotFoundException("User Not found: " + request.getUsername()));
    String jwtToken = jwtService.generateToken(userDetails, user.getId());
    RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
    return AuthResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken.getToken())
        .build();
  }

  @PutMapping("/refresh-token")
  @Hidden
  public AuthResponse refresh(@RequestBody TokenRefreshRequest request) {
    String refreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(refreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshTokenEntity::getUser)
        .map(user -> {
          UserDetails userEntity = userService.loadUserByUsername(user.getEmail());
          String newAccessToken = jwtService.generateToken(userEntity, user.getId());
          return AuthResponse.builder()
              .token(newAccessToken)
              .refreshToken(refreshToken)
              .build();
        }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));

  }


}
