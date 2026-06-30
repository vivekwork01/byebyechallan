package com.byebyechallan.service;

import com.byebyechallan.dto.DocumentRequestDto;
import com.byebyechallan.dto.UserDocumentDto;
import com.byebyechallan.entity.CoreDocumentEntity;
import com.byebyechallan.entity.UserDocumentTEntity;
import com.byebyechallan.entity.UserProfileTEntity;
import com.byebyechallan.repository.DocumentRepository;
import com.byebyechallan.repository.UserDocumentRepository;
import com.byebyechallan.repository.UserProfileRepository;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DocumentService {

  private final DocumentRepository documentRepository;
  private final UserProfileRepository profileRepository;
  private final UserDocumentRepository userDocumentRepository;

  public DocumentService(DocumentRepository documentRepository,
      UserProfileRepository profileRepository, UserDocumentRepository userDocumentRepository) {
    this.documentRepository = documentRepository;
    this.profileRepository = profileRepository;
    this.userDocumentRepository = userDocumentRepository;
  }

  public List<DocumentRequestDto> getDocumentList(String country, String state,
      String registrationType,
      String vehicleType, String docType) {
    String countryStateId = country + "-" + state;

    log.info(
        "Fetching documents for countryStateId: {}, registrationType: {}, vehicleType: {}, docType: {}",
        countryStateId, registrationType, vehicleType, docType);
    List<CoreDocumentEntity> documentEntityList = documentRepository.findAllDocument(countryStateId,
        registrationType, vehicleType,
        docType);
    return documentEntityList.stream().collect(
        ArrayList::new,
        (list, entity) -> list.add(entity.getReqDoc()), ArrayList::addAll
    );
  }

  public UserDocumentDto saveDocument(long userId, long profileId,
      String vehicleRegistrationNo,
      DocumentRequestDto documentRequestDto) {
    UserProfileTEntity userProfileT;
    UserDocumentTEntity userDocumentTEntity;
    try {
      userProfileT = profileRepository.findById(profileId)
          .orElseThrow(() -> new RuntimeException("Profile not found with id: " + profileId));
      if (userProfileT.getUserId() != userId) {
        throw new RuntimeException("Profile does not belong to the user");
      }

      userDocumentTEntity = documentRequestDto.getUserDocEntity(profileId, vehicleRegistrationNo,
          userProfileT);
      userDocumentTEntity = userDocumentRepository.save(userDocumentTEntity);
      log.info("Document saved successfully for userId: {}, profileId: {}, docTemplateId: {}",
          userId, profileId, documentRequestDto.getDocTemplateId());

    } catch (RuntimeException e) {
      log.error("Error occurred while Saving Doc: {}", e.getMessage());
      throw new RuntimeException("Error occurred while Saving Doc: " + e.getMessage());
    }
    return userDocumentTEntity.getUserDocDto();

  }

  public List<UserDocumentDto> getAllDocuments(long userId, long profileId,
      String vehicleRegistrationNo) {
    UserProfileTEntity userProfileT;
    List<UserDocumentDto> userDocumentDtos = new ArrayList<>();
    try {
      userProfileT = profileRepository.findById(profileId)
          .orElseThrow(() -> new RuntimeException("Profile not found with id: " + profileId));
      if (userProfileT.getUserId() != userId) {
        throw new RuntimeException("Profile does not belong to the user");
      }
      List<UserDocumentTEntity> documentTEntities = userDocumentRepository.getAllDocument(userId,
          profileId, vehicleRegistrationNo, false);

      userDocumentDtos = documentTEntities.stream().collect(
          ArrayList::new,
          (list, entity) -> list.add(entity.getUserDocDto()),
          ArrayList::addAll
      );

    } catch (RuntimeException e) {
      log.error("Unable to fetch the document for userId: {}, profileId: {}, registrationNo: {}",
          userId, profileId, vehicleRegistrationNo);
      throw new RuntimeException("failed to fetch", e);
    }
    return userDocumentDtos;
  }
}
