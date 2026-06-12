package com.byebyechallan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CountryDto {
  public String countryId;
  public String countryStateId;
  public String countryName;
}
