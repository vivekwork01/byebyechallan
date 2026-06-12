package com.byebyechallan.utils;

import ch.qos.logback.core.util.StringUtil;

public class Utils {

  public static Boolean isStateRegistration(String registrationType) {
    return !StringUtil.isNullOrEmpty(registrationType) && registrationType.equals("STATE");
  }

  public static Boolean isBHRegistration(String registrationType) {
    return !StringUtil.isNullOrEmpty(registrationType) && registrationType.equals("BH");
  }
}
