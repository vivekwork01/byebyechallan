package com.byebyechallan.service;

import com.byebyechallan.dto.CountryDto;
import com.byebyechallan.dto.RegistrationDto;
import com.byebyechallan.dto.StateDto;
import com.byebyechallan.dto.VehicleTypeResponseDto;
import com.byebyechallan.entity.CoreVehicleTypeEntity;
import com.byebyechallan.repository.CountryStateRepository;
import com.byebyechallan.repository.RegistrationTypeRepository;
import com.byebyechallan.repository.VehicleRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

  private final CountryStateRepository countryStateRepository;
  private final RegistrationTypeRepository registrationTypeRepository;
  private final VehicleRepository vehicleRepository;

  public MasterService(CountryStateRepository countryStateRepository, RegistrationTypeRepository registrationTypeRepository, VehicleRepository vehicleRepository){
    this.countryStateRepository=countryStateRepository;
    this.registrationTypeRepository=registrationTypeRepository;
    this.vehicleRepository=vehicleRepository;
  }


  public List<CountryDto> getCountry() {
    return countryStateRepository.getAllCountry(false);
  }

  public List<StateDto> getStates(String countryId) {
    return countryStateRepository.getAllStateByCountryId(countryId, false);
  }

  public List<RegistrationDto> getAllRegistrationType(String countryId) {
    return registrationTypeRepository.getByCountryIdAndDeleted(countryId, false);
  }

  public List<VehicleTypeResponseDto> getAllVehicleType() {
    List<VehicleTypeResponseDto> dtos;
    try {
      dtos=vehicleRepository.getAllVehicleType(false);
    }catch (Exception e){
      throw new RuntimeException("Unable to fetch the all Vehicle Types");
    }
    return dtos;
  }
}
