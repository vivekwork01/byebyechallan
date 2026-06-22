package com.byebyechallan.auth.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenRefreshRequest {

  private String refreshToken;
}
