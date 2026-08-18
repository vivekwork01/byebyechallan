package com.byebyechallan.dto;

import com.byebyechallan.NotificationChannel;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequestDto {

  private String profileName;
  Map<NotificationChannel, String> recipients;
}
