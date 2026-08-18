package com.byebyechallan.message.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {

  private String documentId;
  private long userId;
  private String recipient;
  private String subject;
  private String body;
  private String vehicleRegistrationNo;
  private String documentName;

}
