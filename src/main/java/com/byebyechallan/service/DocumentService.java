package com.byebyechallan.service;

import static com.byebyechallan.utils.Utils.isBHRegistration;
import static com.byebyechallan.utils.Utils.isStateRegistration;

import com.byebyechallan.dto.CountryDto;
import com.byebyechallan.dto.RegistrationDto;
import com.byebyechallan.dto.StateDto;
import com.byebyechallan.entity.CoreDocumentEntity;
import com.byebyechallan.repository.CountryStateRepository;
import com.byebyechallan.repository.DocumentRepository;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DocumentService {

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private CountryStateRepository countryStateRepository;

  public List<CoreDocumentEntity> getDocument(String country, String state, String registrationType,
      String vehicleType, String docType) {
    Boolean isBh = isBHRegistration(registrationType);
    Boolean isState = isStateRegistration(registrationType);
    String countryStateId = country + "-" + state;

    log.info(
        "Fetching documents for countryStateId: {}, isBh: {}, isState: {}, vehicleType: {}, docType: {}",
        countryStateId, isBh, isState, vehicleType, docType);
    return documentRepository.findAllDocument(countryStateId, isBh, isState, vehicleType, docType);
  }

  public List<CountryDto> getCountry() {
    return countryStateRepository.getAllCountry(false);
  }

  public List<StateDto> getStates(String countryId) {
    return countryStateRepository.getAllStateByCountryId(countryId, false);
  }

  public List<RegistrationDto> getAllRegistrationType(String countryId) {
    return new ArrayList<>();
  }
}
