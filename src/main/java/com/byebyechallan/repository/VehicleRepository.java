package com.byebyechallan.repository;

import com.byebyechallan.dto.VehicleTypeResponseDto;
import com.byebyechallan.entity.CoreVehicleTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<CoreVehicleTypeEntity, String> {

  List<VehicleTypeResponseDto> getAllVehicleType(boolean isDeleted);
}
