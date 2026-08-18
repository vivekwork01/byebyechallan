package com.byebyechallan.message.repository;

import com.byebyechallan.message.entity.CoreJobSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobScheduleRepository extends JpaRepository<CoreJobSchedule, Long> {

  List<CoreJobSchedule> findAllCreatedAndNotDeleted();
}
