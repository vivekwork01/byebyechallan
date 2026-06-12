package com.byebyechallan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StateDto {
  public String countryStateId;
  public String countryId;
  public String stateId;
  public String countryName;
  public String stateName;
}
