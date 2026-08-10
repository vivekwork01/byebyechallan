package com.byebyechallan.dto;

import java.sql.Timestamp;
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
public class RCDto {

  private String registrationNo;
  private Timestamp registrationDate;
  private Timestamp expiryDate;
  private String rcS3Link;
}
