package com.byebyechallan.entity;

import com.byebyechallan.dto.DocumentRequestDto;
import com.byebyechallan.dto.UserDocumentDto;
import jakarta.persistence.*;
import java.time.Instant;
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
@Table(name = "core_document_m")

@NamedQuery(
    name = "CoreDocumentEntity.findAllDocument",
    query = "SELECT c FROM CoreDocumentEntity c " +
        "WHERE c.deleted = false " +
        "AND c.countryStateEntity.countryStateId = :countryStateId " +
        "AND c.registrationCode = :registrationCode " +
        "AND c.vehicleTypeEntity.vehicleTypeId = :vehicleType " +
        "AND (:docType IS NULL OR c.docType = :docType)"
)
public class CoreDocumentEntity {

  @Id
  @Column(name = "doc_id")
  private String docId;

  @Column(name = "doc_name")
  private String docName;

  @Column(name = "registration_code")
  private String registrationCode;

  @Column(name = "doc_type")
  private String docType;

  @Column(name = "is_deleted")
  private Boolean deleted;

  @Column(name = "created_by")
  private long createdBy;

  @Column(name = "created_time")
  private Timestamp createdTime;

  @ManyToOne
  @JoinColumn(name = "country_state_id", insertable = false,
      updatable = false)
  private CoreCountryStateEntity countryStateEntity;


  @ManyToOne
  @JoinColumn(name = "vehicle_type_id", insertable = false,
      updatable = false)
  private CoreVehicleTypeEntity vehicleTypeEntity;

  public DocumentRequestDto getReqDoc() {
    return DocumentRequestDto.builder()
        .docTemplateId(this.docId)
        .docName(this.docName)
        .docId(this.docId)
        .sms(false)
        .email(false)
        .whatsApp(false)
        .expiryDate(Timestamp.from(Instant.now()))
        .notificationTime(Timestamp.from(Instant.now()))
        .uploaded(false)
        .build();
  }
}
