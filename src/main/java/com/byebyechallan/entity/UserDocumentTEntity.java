package com.byebyechallan.entity;

import com.byebyechallan.dto.UserDocumentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_document_t")

@NamedQuery(name = "UserDocumentTEntity.getAllDocument",
    query = "SELECT u FROM UserDocumentTEntity u " +
        "WHERE u.deleted = :isDeleted " +
        "AND u.userProfile.userId = :userId " +
        "AND u.profileId = :profileId " +
        "AND u.vehicleRegistrationNo = :vehicleRegistrationNo")

public class UserDocumentTEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "profile_id")
  private Long profileId;

  @Column(name = "vehicle_registration_no")
  private String vehicleRegistrationNo;

  @Column(name = "doc_template_id")
  private String docTemplateId;

  @Column(name = "doc_name")
  private String docName;

  @Column(name = "doc_id")
  private String docId;

  @Column(name = "doc_s3_upload")
  private String docS3Upload;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "uploaded_date")
  private Timestamp uploadedDate;

  @Column(name = "expiry_date")
  private Timestamp expiryDate;

  @Column(name = "is_sms")
  private Boolean sms;

  @Column(name = "is_email")
  private Boolean email;

  @Column(name = "is_whatsapp")
  private Boolean whatsapp;

  @Column(name = "notification_time")
  private Timestamp notificationTime;

  @Column(name = "is_uploaded")
  private Boolean uploaded;

  @Column(name = "is_deleted")
  private Boolean deleted;

  @Column(name = "created_time")
  private Timestamp createdTime;

  @Column(name = "updated_time")
  private Timestamp updatedTime;

  @ManyToOne
  @JoinColumn(name = "profile_id", insertable = false, updatable = false)
  private UserProfileTEntity userProfile;

  public UserDocumentDto getUserDocDto() {
    return UserDocumentDto.builder()
        .id(this.id)
        .userId(this.userProfile.getUserId())
        .profileId(this.profileId)
        .vehicleRegistrationNo(this.vehicleRegistrationNo)
        .docTemplateId(this.docTemplateId)
        .docName(this.docName)
        .docId(this.docId)
        .s3Link(this.docS3Upload)
        .fileName(this.fileName)
        .uploadedDate(this.uploadedDate)
        .expiryDate(this.expiryDate)
        .uploaded(this.uploaded)
        .sms(this.sms)
        .email(this.email)
        .whatsApp(this.whatsapp)
        .notificationTime(this.notificationTime)
        .createDate(this.createdTime)
        .updatedDate(this.updatedTime)
        .build();
  }
}

