package com.byebyechallan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "user_profile_t")
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

}

