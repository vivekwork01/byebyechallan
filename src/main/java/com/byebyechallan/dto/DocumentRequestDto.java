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

  private Long id;
  private String docTemplateId;
  private String docName;
  private String docId;
  private boolean sms;
  private boolean email;
  private boolean whatsApp;
  private Timestamp expiryDate;
  private Timestamp notificationTime;
  private boolean renewable;
  private String fileName;
  private String s3FileName;
  private boolean uploaded;

  public UserDocumentTEntity getUserDocEntity(Long profileId, String vehicleRegistrationNo,
      UserProfileTEntity userProfileT, RCDto rcDto, String s3Link) {
    UserDocumentTEntity.UserDocumentTEntityBuilder builder = UserDocumentTEntity.builder()
        .profileId(profileId)
        .docTemplateId(this.docTemplateId)
        .docName(this.docName)
        .docId(this.docId)
        .vehicleRegistrationNo(vehicleRegistrationNo)
        .expiryDate(!this.renewable && null != rcDto ? rcDto.getExpiryDate() : this.expiryDate)
        .renewable(this.renewable)
        .uploadedDate(Timestamp.from(new Date().toInstant()))
        .docS3Upload(s3Link != null ? s3Link : "No Link Available")
        .fileName(this.fileName != null ? this.fileName : "No File Name")
        .notificationTime(this.notificationTime)
        .uploaded(this.uploaded)
        .sms(this.sms)
        .email(this.email)
        .whatsapp(this.whatsApp)
        .sms(this.sms)
        .deleted(false)
        .createdTime(Timestamp.from(new Date().toInstant()))
        .updatedTime(Timestamp.from(new Date().toInstant()))
        .userProfile(userProfileT);
    if (this.id != null && this.id != 0) {
      builder.id(this.id);
    }
    return builder.build();
  }
}
