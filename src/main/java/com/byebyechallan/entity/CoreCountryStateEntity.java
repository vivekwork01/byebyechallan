package com.byebyechallan.entity;

import com.byebyechallan.dto.CountryDto;
import com.byebyechallan.dto.StateDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
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
@Table(name = "core_country_state_m")

@NamedQuery(name = "CoreCountryStateEntity.getAllStateByCountryId",
    query = "SELECT c.countryStateId, c.countryId, c.stateId, c.countryName, c.stateName FROM CoreCountryStateEntity c WHERE c.countryId=:countryId AND c.deleted=:isDeleted",
    resultClass = StateDto.class)
@NamedQuery(name = "CoreCountryStateEntity.getAllCountry",
    query = "SELECT DISTINCT c.countryId, c.countryStateId, c.countryName FROM CoreCountryStateEntity c WHERE c.deleted=:isDeleted",
    resultClass = CountryDto.class)

public class CoreCountryStateEntity {

  @Id
  @Column(name = "country_state_id")
  private String countryStateId;

  @Column(name = "country_id")
  private String countryId;

  @Column(name = "state_id")
  private String stateId;

  @Column(name = "country_name")
  private String countryName;

  @Column(name = "state_name")
  private String stateName;

  @Column(name = "is_deleted")
  private Boolean deleted;

  @Column(name = "created_by")
  private long createdBy;

  @Column(name = "created_time")
  private Timestamp createdTime;


}
