package com.byebyechallan.repository;

import com.byebyechallan.dto.RegistrationDto;
import com.byebyechallan.entity.CoreRegistrationTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTypeRepository extends JpaRepository<CoreRegistrationTypeEntity, Long> {

  List<RegistrationDto> getByCountryIdAndIsDeleted(String countryId, boolean isDeleted);
}
