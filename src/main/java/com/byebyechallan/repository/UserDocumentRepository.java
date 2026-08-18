package com.byebyechallan.repository;

import com.byebyechallan.dto.UserDocumentDto;
import com.byebyechallan.entity.UserDocumentTEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocumentTEntity, Long> {

  List<UserDocumentTEntity> getAllDocument(long userId, long profileId,
      String vehicleRegistrationNo, boolean isDeleted);

  List<UserDocumentTEntity> getNonRenewDoc(long userId, long profileId,
      String vehicleRegistrationNo, boolean isDeleted, boolean renewable);

  List<UserDocumentTEntity> findAllExpiringInMonth(LocalDateTime startDate, LocalDateTime endDate);
}
