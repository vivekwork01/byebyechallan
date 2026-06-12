package com.byebyechallan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "core_user_m")
public class CoreUserMEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "mobile")
  private String mobile;

  @Column(name = "password")
  private String password;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  @Column(name = "created_time")
  private Timestamp createdTime;

  @Column(name = "updated_time")
  private Timestamp updatedTime;

}

