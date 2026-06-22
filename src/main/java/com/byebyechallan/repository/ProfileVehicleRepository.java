package com.byebyechallan.repository;

import com.byebyechallan.entity.CoreProfileVehicleTr;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileVehicleRepository extends JpaRepository<CoreProfileVehicleTr, Long> {

  List<CoreProfileVehicleTr> getAllProfileVehicle(long profileId, boolean isDeleted);
}
