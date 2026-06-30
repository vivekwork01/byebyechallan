package com.byebyechallan.auth.service;

import com.byebyechallan.auth.entity.RefreshTokenEntity;
import com.byebyechallan.auth.repository.RefreshTokenRepository;
import com.byebyechallan.auth.repository.UserRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

  // 7 Days lifetime for refresh tokens
  private final Long REFRESH_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 7;

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
      UserRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
  }

  public Optional<RefreshTokenEntity> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshTokenEntity createRefreshToken(String emailId) {
    var user = userRepository.findByEmailAndDeleted(emailId, false)
        .orElseThrow(() -> new RuntimeException("User not found: " + emailId));
    refreshTokenRepository.deleteByUser(user);

    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
        .user(user)
        .expiryTime(Instant.now().plusMillis(REFRESH_EXPIRATION_MS))
        .token(UUID.randomUUID().toString())
        .build();
    refreshTokenRepository.save(refreshToken);

    return refreshToken;
  }

  public RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshToken) {
    if (refreshToken.getExpiryTime().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(refreshToken);
      throw new RuntimeException("Refresh token was expired. Please make a new signin request");
    }
    return refreshToken;
  }
}
