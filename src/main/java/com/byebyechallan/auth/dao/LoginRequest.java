package com.byebyechallan.auth.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  private String username;
  private String password;
}
