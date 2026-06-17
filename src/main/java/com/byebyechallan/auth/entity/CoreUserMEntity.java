package com.byebyechallan.auth.entity;

import com.byebyechallan.auth.enums.Role;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "core_user_m")
public class CoreUserMEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "mobile")
  private String mobile;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @Builder.Default
  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  @Column(name = "created_time")
  private Timestamp createdTime;

  @Column(name = "updated_time")
  private Timestamp updatedTime;

  @PrePersist
  protected void onCreate() {
    if (isDeleted == null) isDeleted = false;
    if (createdTime == null) createdTime = new Timestamp(System.currentTimeMillis());
    updatedTime = createdTime;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedTime = new Timestamp(System.currentTimeMillis());
  }

  @Override
  public String toString() {
    return "Id: " + this.id + " Name: " + this.name + " Email: " + this.email + " Mobile: "
        + this.mobile + " Role: " + this.role;
  }

}

