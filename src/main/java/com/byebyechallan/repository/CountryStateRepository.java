package com.byebyechallan.repository;

import com.byebyechallan.dto.CountryDto;
import com.byebyechallan.dto.StateDto;
import com.byebyechallan.entity.CoreCountryStateEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryStateRepository extends JpaRepository<CoreCountryStateEntity, String> {

  List<CountryDto> getAllCountry(boolean isDeleted);

  List<StateDto> getAllStateByCountryId(String countryId, boolean isDeleted);
}
