package com.byebyechallan.message.entity;

import com.byebyechallan.NotificationChannel;
import com.byebyechallan.message.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "core_job_schedule_t")

@NamedQuery(name = "CoreJobSchedule.findAllCreatedAndNotDeleted", query = "SELECT j FROM CoreJobSchedule j WHERE status IN ('CREATED', 'PENDING', 'FAILED') AND j.isDeleted = false")

public class CoreJobSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "job_id")
  private long jobId;

  @Column(name = "channel")
  @Enumerated(EnumType.STRING)
  private NotificationChannel channel;

  @Column(name = "notification_event")
  private String notificationEvent;

  @Column(name = "schedule_at")
  private Timestamp scheduleAt;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private TaskStatus status;

  @Column(name = "retry_count")
  private int retryCount;

  @Column(name = "is_deleted")
  private boolean isDeleted;

  @Column(name = "created_time")
  private Timestamp createdTime;

  @Column(name = "updated_time")
  private Timestamp updatedTime;


  @PrePersist
  protected void onCreate() {
    this.isDeleted = false;
    this.scheduleAt = Timestamp.valueOf(LocalDateTime.now());
    this.createdTime = Timestamp.valueOf(LocalDateTime.now());
    this.updatedTime = Timestamp.valueOf(LocalDateTime.now());
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedTime = Timestamp.valueOf(LocalDateTime.now());
  }

}




