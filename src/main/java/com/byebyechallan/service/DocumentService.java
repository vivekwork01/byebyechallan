package com.byebyechallan.service;

import com.byebyechallan.dto.DocumentRequestDto;
import com.byebyechallan.dto.RCDto;
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
  private final InMemoryFileService fileService;

  public DocumentService(DocumentRepository documentRepository,
      UserProfileRepository profileRepository, UserDocumentRepository userDocumentRepository,
      InMemoryFileService fileService) {
    this.documentRepository = documentRepository;
    this.profileRepository = profileRepository;
    this.userDocumentRepository = userDocumentRepository;
    this.fileService = fileService;
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
      DocumentRequestDto documentRequestDto, RCDto rcDto) {
    UserProfileTEntity userProfileT;
    UserDocumentTEntity userDocumentTEntity;
    List<UserDocumentTEntity> userDocumentTEntities = new ArrayList<>();
    try {
      userProfileT = profileRepository.findById(profileId)
          .orElseThrow(() -> new RuntimeException("Profile not found with id: " + profileId));
      if (userProfileT.getUserId() != userId) {
        throw new RuntimeException("Profile does not belong to the user");
      }

      String docTemplateId = documentRequestDto.getDocTemplateId();
      String s3Link = documentRequestDto.getS3FileName();

      // If the Doc is Certificate and Registration, then update the expiry date of all the non-renewed documents for that profile and vehicle registration number
      if (docTemplateId.contains("Certificate") && docTemplateId.contains("Registration")) {
        s3Link = rcDto.getRcS3Link();
        userDocumentTEntities = userDocumentRepository.getNonRenewDoc(userId, profileId, vehicleRegistrationNo, false, false);
        userDocumentTEntities.forEach(doc -> {
          doc.setExpiryDate(rcDto.getExpiryDate());
          userDocumentRepository.save(doc);
        });
      }
      userDocumentTEntity = documentRequestDto.getUserDocEntity(profileId, vehicleRegistrationNo,
          userProfileT, rcDto, resolveS3Link(userId, s3Link));
      userDocumentTEntities.add(userDocumentTEntity);
      userDocumentRepository.saveAll(userDocumentTEntities);
      log.info("Document saved successfully for userId: {}, profileId: {}, docTemplateId: {}",
          userId, profileId, documentRequestDto.getDocTemplateId());

    } catch (RuntimeException e) {
      log.error("Error occurred while Saving Doc: {}", e.getMessage());
      throw new RuntimeException("Error occurred while Saving Doc: " + e.getMessage());
    }
    return userDocumentTEntity.getUserDocDto();
  }

  public UserDocumentTEntity buildUserDocEntity(Long profileId, String vehicleRegistrationNo,
      UserProfileTEntity userProfileT, RCDto rcDto, String s3Link,
      DocumentRequestDto documentRequestDto) {
    return documentRequestDto.getUserDocEntity(profileId, vehicleRegistrationNo, userProfileT,
        rcDto, resolveS3Link(userProfileT.getUserId(), s3Link));
  }


  private String resolveS3Link(long userId, String fileName) {
    if (fileName == null || fileName.isBlank() || "No File Name".equals(fileName)) {
      return "No Link Available";
    }
    return fileService.buildFileUrl(userId, fileName);
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
