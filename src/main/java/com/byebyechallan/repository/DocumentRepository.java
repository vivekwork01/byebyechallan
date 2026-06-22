package com.byebyechallan.repository;

import com.byebyechallan.entity.CoreDocumentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentRepository extends JpaRepository<CoreDocumentEntity, Long> {


  List<CoreDocumentEntity> findAllDocument(String countryStateId, String registrationCode,
      String vehicleType, String docType);
}
