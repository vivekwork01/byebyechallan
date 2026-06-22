package com.byebyechallan.controller;

import com.byebyechallan.dto.CountryDto;
import com.byebyechallan.dto.RegistrationDto;
import com.byebyechallan.dto.StateDto;
import com.byebyechallan.service.MasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/master")
@Tag(name = "Master Controller", description = "Controller for master data management")
public class MasterController {

  private final MasterService masterService;

  public MasterController(MasterService masterService) {
    this.masterService = masterService;
  }

  @GetMapping("/country")
  @Operation(summary = "Get all Countries", description = "Get all Countries")
  public List<CountryDto> getCountry() {
    return masterService.getCountry();
  }

  @GetMapping("/country/{countryId}")
  @Operation(summary = "Get State for Selected Country", description = "Get all State for selected country")
  public List<StateDto> getStates(@PathVariable("countryId") String countryId) {
    return masterService.getStates(countryId);
  }

  @GetMapping("/country/{countryId}/registration")
  @Operation(summary = "Get all Registration Type for a Country", description = "Get all Registration Type for a Country")
  public List<RegistrationDto> getAllRegistrationType(@PathVariable("countryId") String countryId) {
    return masterService.getAllRegistrationType(countryId);
  }

}
