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

@NamedQuery(name = "UserProfileTEntity.getAllProfile",
    query = "SELECT u FROM UserProfileTEntity u WHERE u.userId = :userId AND u.isDeleted = false")
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
  private Boolean isDeleted;

  @Column(name = "created_time")
  private Timestamp createdTime;

  public ProfileDto getProfileDto() {
    return ProfileDto.builder()
        .id(this.id)
        .userId(this.userId)
        .profileName(this.name)
        .build();
  }
}

