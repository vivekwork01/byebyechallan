package com.byebyechallan.entity;

import com.byebyechallan.NotificationChannel;
import com.byebyechallan.dto.ProfileDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_profile_t")

@NamedNativeQuery(name = "UserProfileTEntity.getAllProfile",
    query =
        "SELECT pt.id id, pt.user_id user_id, pt.name profile_name, COUNT(DISTINCT pvt.id) vehicle_count,  pt.notification_recipients recipients "
            + "FROM user_profile_t pt "
            + "LEFT JOIN core_profile_vehicle_tr pvt ON pvt.profile_id=pt.id AND pt.is_deleted=0 and pvt.is_deleted=0 "
            + "where pt.user_id=:userId GROUP BY pt.id",
    resultClass = ProfileDto.class)
public class UserProfileTEntity {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "name")
  private String name;

  @Column(name = "notification_recipients")
  private String notificationRecipients;

  @Column(name = "is_deleted")
  private Boolean deleted;

  @Column(name = "created_time")
  private Timestamp createdTime;

  public ProfileDto getProfileDto() throws JsonProcessingException {
    return ProfileDto.builder()
        .id(this.id)
        .userId(this.userId)
        .profileName(this.name)
        .notification_recipients(objectMapper.readValue(this.notificationRecipients,
            objectMapper.getTypeFactory()
                .constructMapType(Map.class, NotificationChannel.class, String.class)))
        .vehicleCount(0)
        .build();
  }
}

