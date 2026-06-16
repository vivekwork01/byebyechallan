package com.byebyechallan.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

  private final String token;
  private final String refreshToken;
}
