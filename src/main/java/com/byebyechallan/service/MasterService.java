package com.byebyechallan.service;

import com.byebyechallan.dto.CountryDto;
import com.byebyechallan.dto.RegistrationDto;
import com.byebyechallan.dto.StateDto;
import com.byebyechallan.repository.CountryStateRepository;
import com.byebyechallan.repository.RegistrationTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

  private final CountryStateRepository countryStateRepository;
  private final RegistrationTypeRepository registrationTypeRepository;

  public MasterService(CountryStateRepository countryStateRepository, RegistrationTypeRepository registrationTypeRepository){
    this.countryStateRepository=countryStateRepository;
    this.registrationTypeRepository=registrationTypeRepository;
  }


  public List<CountryDto> getCountry() {
    return countryStateRepository.getAllCountry(false);
  }

  public List<StateDto> getStates(String countryId) {
    return countryStateRepository.getAllStateByCountryId(countryId, false);
  }

  public List<RegistrationDto> getAllRegistrationType(String countryId) {
    return registrationTypeRepository.getByCountryIdAndIsDeleted(countryId, false);
  }

}
