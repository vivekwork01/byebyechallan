package com.byebyechallan.dto;


import com.byebyechallan.NotificationChannel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProfileDto {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Id
  private long id;

  private long userId;
  private String profileName;
  private int vehicleCount;
  private String recipients;

  @Transient
  private Map<NotificationChannel, String> notification_recipients;

  @PostLoad
  private void postLoad() throws JsonProcessingException {
    this.notification_recipients = objectMapper.readValue(this.recipients,
        objectMapper.getTypeFactory()
            .constructMapType(Map.class, NotificationChannel.class, String.class));
  }

}
