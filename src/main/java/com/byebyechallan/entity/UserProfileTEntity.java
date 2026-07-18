package com.byebyechallan.entity;

import com.byebyechallan.dto.ProfileDto;
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
@Table(name = "user_profile_t")

@NamedNativeQuery(name = "UserProfileTEntity.getAllProfile",
    query = "SELECT pt.id id, pt.user_id user_id, pt.name profile_name, COUNT(DISTINCT dt.vehicle_registration_no) vehicle_count "
        + "FROM user_profile_t pt "
        + "LEFT JOIN user_document_t dt ON dt.profile_id=pt.id AND pt.is_deleted=0 and dt.is_deleted=0 "
        + "where pt.user_id=:userId GROUP BY pt.id",
    resultClass = ProfileDto.class)
public class UserProfileTEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "name")
  private String name;

  @Column(name = "is_deleted")
  private Boolean deleted;

  @Column(name = "created_time")
  private Timestamp createdTime;

  public ProfileDto getProfileDto() {
    return ProfileDto.builder()
        .id(this.id)
        .userId(this.userId)
        .profileName(this.name)
        .vehicleCount(0)
        .build();
  }
}

