package com.byebyechallan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "user_document_t")
public class UserDocumentTEntity {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "profile_id")
  private Long profileId;

  @Column(name = "doc_template_id")
  private String docTemplateId;

  @Column(name = "doc_holder_name")
  private String docHolderName;

  @Column(name = "doc_name")
  private String docName;

  @Column(name = "doc_id")
  private String docId;

  @Column(name = "doc_s3_upload")
  private String docS3Upload;

  @Column(name = "uploaded_date")
  private Timestamp uploadedDate;

  @Column(name = "expiry_date")
  private Timestamp expiryDate;

  @Column(name = "is_sms")
  private Boolean isSms;

  @Column(name = "is_email")
  private Boolean isEmail;

  @Column(name = "is_whatsapp")
  private Boolean isWhatsapp;

  @Column(name = "notification_time")
  private Timestamp notificationTime;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  @Column(name = "created_time")
  private Timestamp createdTime;

  @Column(name = "updated_time")
  private Timestamp updatedTime;

  @ManyToOne
  @JoinColumn(name = "profile_id", insertable = false, updatable = false)
  private UserProfileTEntity userProfile;

}

