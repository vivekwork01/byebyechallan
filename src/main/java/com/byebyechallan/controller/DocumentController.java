package com.byebyechallan.controller;

import com.byebyechallan.dto.CountryDto;
import com.byebyechallan.dto.RegistrationDto;
import com.byebyechallan.dto.StateDto;
import com.byebyechallan.entity.CoreDocumentEntity;
import com.byebyechallan.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/master")
public class DocumentController {

  @Autowired
  private DocumentService documentService;

  @GetMapping("/document")
  public List<CoreDocumentEntity> getDocument(
      @RequestParam(name = "country", required = true) String country,
      @RequestParam(name = "state", required = true) String state,
      @RequestParam(name = "registration_type", required = true) String registrationType,
      @RequestParam("vehicle_type") String vehicleType,
      @RequestParam(name = "doc_type", required = false) String docType) {
    return documentService.getDocument(country, state, registrationType, vehicleType, docType);
  }

  @GetMapping("/country")
  public List<CountryDto> getCountry() {
    return documentService.getCountry();
  }

  @GetMapping("/country/{countryId}")
  public List<StateDto> getStates(@PathVariable("countryId") String countryId) {
    return documentService.getStates(countryId);
  }

  @GetMapping("/country/{countryId}/registration")
  public List<RegistrationDto> getAllRegistrationType(@PathVariable("countryId") String countryId) {
    return documentService.getAllRegistrationType(countryId);
  }

}
