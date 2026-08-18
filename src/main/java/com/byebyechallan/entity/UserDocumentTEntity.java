package com.byebyechallan.entity;

import com.byebyechallan.NotificationChannel;
import com.byebyechallan.dto.UserDocumentDto;
import com.byebyechallan.message.dto.NotificationEvent;
import com.byebyechallan.message.entity.CoreJobSchedule;
import com.byebyechallan.message.enums.TaskStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

@NamedQuery(name = "UserDocumentTEntity.getNonRenewDoc",
    query = "SELECT u FROM UserDocumentTEntity u " +
        "WHERE u.deleted = :isDeleted " +
        "AND u.userProfile.userId = :userId " +
        "AND u.profileId = :profileId " +
        "AND u.vehicleRegistrationNo = :vehicleRegistrationNo "
        + "AND u.renewable = :renewable")

@NamedQuery(name = "UserDocumentTEntity.findAllExpiringInMonth",
    query = "SELECT u FROM UserDocumentTEntity u " +
        "WHERE u.deleted = :isDeleted " +
        "AND u.expiryDate BETWEEN :startDate AND :endDate")

public class UserDocumentTEntity {

  private static final ObjectMapper objectMapper = new ObjectMapper();

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

  @Column(name = "is_renewable")
  private Boolean renewable;

  @Column(name = "is_sms")
  private Boolean sms;

  @Column(name = "is_email")
  private Boolean email;

  @Column(name = "is_whatsapp")
  private Boolean whatsapp;


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
        .renewable(this.renewable)
        .uploaded(this.uploaded)
        .sms(this.sms)
        .email(this.email)
        .whatsApp(this.whatsapp)
        .createDate(this.createdTime)
        .updatedDate(this.updatedTime)
        .build();
  }

  public List<CoreJobSchedule> getScheduler() throws JsonProcessingException {
    List<CoreJobSchedule> jobSchedules = new ArrayList<>();
    if (this.email) {
      jobSchedules.add(CoreJobSchedule.builder()
          .channel(NotificationChannel.EMAIL)
          .notificationEvent(getContent(NotificationChannel.EMAIL))
          .scheduleAt(Timestamp.valueOf(LocalDateTime.now()))
          .status(TaskStatus.CREATED)
          .retryCount(0)
          .isDeleted(false)
          .build());
    }
    if (this.sms) {
      jobSchedules.add(CoreJobSchedule.builder()
          .channel(NotificationChannel.SMS)
          .notificationEvent(getContent(NotificationChannel.SMS))
          .scheduleAt(Timestamp.valueOf(LocalDateTime.now()))
          .status(TaskStatus.CREATED)
          .retryCount(0)
          .isDeleted(false)
          .build());
    }
    if (this.whatsapp) {
      jobSchedules.add(CoreJobSchedule.builder()
          .channel(NotificationChannel.WHATSAPP)
          .notificationEvent(getContent(NotificationChannel.WHATSAPP))
          .scheduleAt(Timestamp.valueOf(LocalDateTime.now()))
          .status(TaskStatus.CREATED)
          .retryCount(0)
          .isDeleted(false)
          .build());
    }
    return jobSchedules;
  }

  private String getContent(NotificationChannel notificationChannel) throws JsonProcessingException {
    Map<NotificationChannel, String> recipients = objectMapper.readValue(this.userProfile.getNotificationRecipients(), new TypeReference<Map<NotificationChannel, String>>() {});
    NotificationEvent event=NotificationEvent.builder()
        .documentId(this.docId)
        .userId(this.getUserProfile().getUserId())
        .recipient(recipients.get(notificationChannel))
        .subject("Document Expiry Notification")
        .body(getMessageBody())
        .vehicleRegistrationNo(this.vehicleRegistrationNo)
        .documentName(this.docName)
        .build();

    return objectMapper.writeValueAsString(event);

  }

  private String getMessageBody() {
    String documentName = docName != null ? docName : "document";
    String vehicleNo = vehicleRegistrationNo != null ? vehicleRegistrationNo : "your vehicle";
    String profileName = userProfile != null && userProfile.getName() != null
        ? userProfile.getName()
        : "there";

    if (expiryDate == null) {
      return "Hello " + profileName + ",\n\n"
          + "This is a friendly reminder from ByeByeChallan that your "
          + documentName + " for vehicle " + vehicleNo + " is approaching its expiry date.\n\n"
          + "Please open the app to review the document details and take action before it expires "
          + "to avoid fines or challans.\n\n"
          + "Thank you for keeping your vehicle documents up to date!";
    }

    LocalDate expiry = expiryDate.toLocalDateTime().toLocalDate();
    long daysUntilExpiry = ChronoUnit.DAYS.between(LocalDate.now(), expiry);
    String expiryFormatted = expiryDate.toLocalDateTime()
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

    String expiryStatus;
    if (daysUntilExpiry < 0) {
      expiryStatus = "expired " + Math.abs(daysUntilExpiry) + " day(s) ago";
    } else if (daysUntilExpiry == 0) {
      expiryStatus = "expires today";
    } else if (daysUntilExpiry == 1) {
      expiryStatus = "expires tomorrow";
    } else {
      expiryStatus = "expires in " + daysUntilExpiry + " day(s)";
    }

    String renewalAdvice = Boolean.TRUE.equals(renewable)
        ? "This document is renewable. Please upload the renewed copy in the ByeByeChallan app "
            + "before the expiry date to stay compliant."
        : "Please ensure you have a valid document on hand to avoid traffic challans and penalties.";

    return "Hello " + profileName + ",\n\n"
        + "This is a reminder from ByeByeChallan about your vehicle document.\n\n"
        + "Document: " + documentName + "\n"
        + "Vehicle: " + vehicleNo + "\n"
        + "Status: " + expiryStatus + "\n"
        + "Expiry date: " + expiryFormatted + "\n\n"
        + renewalAdvice + "\n\n"
        + "Open the ByeByeChallan app to view or update your documents.\n\n"
        + "Stay safe and drive responsibly!";
  }
}

