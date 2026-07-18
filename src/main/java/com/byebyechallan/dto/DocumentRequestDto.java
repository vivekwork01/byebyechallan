package com.byebyechallan.dto;


import com.byebyechallan.entity.UserDocumentTEntity;
import com.byebyechallan.entity.UserProfileTEntity;
import java.sql.Timestamp;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequestDto {

  private long id;
  private String docTemplateId;
  private String docName;
  private String docId;
  private boolean sms;
  private boolean email;
  private boolean whatsApp;
  private Timestamp expiryDate;
  private Timestamp notificationTime;
  private boolean uploaded;

  public UserDocumentTEntity getUserDocEntity(Long profileId, String vehicleRegistrationNo,
      UserProfileTEntity userProfileT) {
    return UserDocumentTEntity.builder()
        .id(this.id)
        .profileId(profileId)
        .docTemplateId(this.docTemplateId)
        .docName(this.docName)
        .docId(this.docId)
        .vehicleRegistrationNo(vehicleRegistrationNo)
        .expiryDate(this.expiryDate)
        .uploadedDate(Timestamp.from(new Date().toInstant()))
        .docS3Upload(
            "url_xxx") // Placeholder for S3 upload URL, should be replaced with actual logic to upload and get the URL
        .notificationTime(this.notificationTime)
        .uploaded(this.uploaded)
        .sms(this.sms)
        .email(this.email)
        .whatsapp(this.whatsApp)
        .sms(this.sms)
        .deleted(false)
        .createdTime(Timestamp.from(new Date().toInstant()))
        .updatedTime(Timestamp.from(new Date().toInstant()))
        .userProfile(userProfileT)
        .build();
  }
}
