package com.byebyechallan.entity;

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
@Table(name = "core_document_m")

@NamedQuery(
    name = "CoreDocumentEntity.findAllDocument",
    query = "SELECT c FROM CoreDocumentEntity c " +
        "WHERE c.isDeleted = false " +
        "AND c.countryStateEntity.countryStateId = :countryStateId " +
        "AND c.isBH = :isBH " +
        "AND c.isState = :isState " +
        "AND c.vehicleTypeEntity.vehicleTypeId = :vehicleType " +
        "AND c.docType = :docType"
)
public class CoreDocumentEntity {

  @Id
  @Column(name = "doc_id")
  private String docId;

  @Column(name = "doc_name")
  private String docName;

  @Column(name = "is_bh")
  private Boolean isBH;

  @Column(name = "is_state")
  private Boolean isState;

  @Column(name = "doc_type")
  private String docType;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

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


}
