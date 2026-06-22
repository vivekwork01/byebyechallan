package com.byebyechallan.entity;

import com.byebyechallan.dto.ProfileVehicleResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "core_profile_vehicle_tr")

@NamedQuery(name = "CoreProfileVehicleTr.getAllProfileVehicle",
    query = "SELECT ent FROM CoreProfileVehicleTr ent "
        + "WHERE ent.profileId=:profileId "
        + "AND ent.isDeleted=:isDeleted")
public class CoreProfileVehicleTr {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "profile_id", nullable = false)
  private Long profileId;

  @Column(name = "vehicle_name", nullable = false, length = 128)
  private String vehicleName;

  @Column(name = "vehicle_registration_no", nullable = false, length = 129)
  private String vehicleRegistrationNo;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "created_time", updatable = false)
  private LocalDateTime createdTime;

  @Column(name = "updated_time")
  private LocalDateTime updatedTime;

  @ManyToOne
  @JoinColumn(name = "profile_id", insertable = false, updatable = false)
  private UserProfileTEntity userProfile;

  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.createdTime = now;
    this.updatedTime = now;
    this.isDeleted=false;
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedTime = LocalDateTime.now();
  }

  public ProfileVehicleResponseDto getProfileVehicleDto() {
    return ProfileVehicleResponseDto.builder()
        .id(this.id)
        .profileVehicleName(this.vehicleName)
        .vehicleRegistrationNo(this.vehicleRegistrationNo)
        .build();

  }
}