package com.byebyechallan.dto;


import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDocumentDto {

  private long id;
  private long userId;
  private long profileId;
  private String vehicleRegistrationNo;
  private String docTemplateId;
  private String docId;
  private String docName;
  private String s3Link;
  private Timestamp uploadedDate;
  private Timestamp expiryDate;
  private boolean isSms;
  private boolean isEmail;
  private boolean isWhatsApp;
  private Timestamp notificationTime;
  private Timestamp createDate;
  private Timestamp updatedDate;



}
