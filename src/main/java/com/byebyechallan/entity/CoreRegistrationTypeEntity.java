package com.byebyechallan.entity;

import com.byebyechallan.dto.RegistrationDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "core_registration_type_m")
@NamedQuery(name = "CoreRegistrationTypeEntity.getByCountryIdAndIsDeleted", query = "SELECT crt.registrationCode registrationCode, crt.registrationType registrationType FROM CoreRegistrationTypeEntity crt WHERE crt.countryId=:countryId AND crt.isDeleted=:isDeleted", resultClass = RegistrationDto.class)
public class CoreRegistrationTypeEntity {

  @Id
  @Column(name = "registration_code")
  public String registrationCode;

  @Column(name="country_id")
  public String countryId;

  @Column(name = "registration_type")
  public String registrationType;

  @Column(name = "is_deleted")
  public Boolean isDeleted;

}
