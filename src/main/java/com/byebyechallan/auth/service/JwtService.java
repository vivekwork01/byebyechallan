package com.byebyechallan.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String SECRET_KEY = "bW9yZV9zZWNyZXRfa2V5X3dpdGhfaGlnaF9lbnRyb3B5XzEyMzQ1Njc4OTBfQUJDREVG";
  private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60; // 1 hours
  private static final long CLOCK_SKEW_MS = 1000 * 60 * 5; // 5 minutes clock skew tolerance

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", userDetails.getAuthorities().toString());
    return generateToken(claims, userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
        .signWith(getSigningKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractEmail(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
    final Claims claims = Jwts.parser()
        .verifyWith(getSigningKey())
        .clockSkewSeconds(CLOCK_SKEW_MS / 1000)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claimResolver.apply(claims);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String email = extractEmail(token);
    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date(System.currentTimeMillis() - CLOCK_SKEW_MS));
  }

  private Date extractExpiration(String token) {
    return extractClaims(token, Claims::getExpiration);
  }
}
