package com.byebyechallan.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "core_vehicle_category_m")
public class CoreVehicleCategoryEntity {

  @Id
  @Column(name = "vehicle_category_id")
  private String vehicleCategoryId;

  @Column(name = "vehicle_category_name")
  private String vehicleCategoryName;

  @Column(name = "is_deleted")
  private Boolean deleted;

  @Column(name = "created_by")
  private long createdBy;

  @Column(name = "created_time")
  private java.sql.Timestamp createdTime;
}
