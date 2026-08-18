package com.byebyechallan.service;

import com.byebyechallan.dto.ProfileDto;
import com.byebyechallan.dto.ProfileRequestDto;
import com.byebyechallan.dto.ProfileVehicleResponseDto;
import com.byebyechallan.dto.UserDocumentDto;
import com.byebyechallan.dto.VehicleRequestDto;
import com.byebyechallan.entity.CoreProfileVehicleTr;
import com.byebyechallan.entity.UserDocumentTEntity;
import com.byebyechallan.entity.UserProfileTEntity;
import com.byebyechallan.repository.ProfileVehicleRepository;
import com.byebyechallan.repository.UserDocumentRepository;
import com.byebyechallan.repository.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final UserProfileRepository userProfileRepository;
  private final UserDocumentRepository userDocumentRepository;
  private final ProfileVehicleRepository profileVehicleRepo;
  private final DocumentService documentService;

  public UserProfileService(UserProfileRepository userProfileRepository,
      UserDocumentRepository userDocumentRepository,
      ProfileVehicleRepository profileVehicleRepo, DocumentService documentService) {
    this.userProfileRepository = userProfileRepository;
    this.userDocumentRepository = userDocumentRepository;
    this.profileVehicleRepo = profileVehicleRepo;
    this.documentService = documentService;
  }

  public ProfileDto createProfile(long userId, ProfileRequestDto profileRequestDto) {
    ProfileDto profileDto = null;

    try {
      UserProfileTEntity userProfileTEntity = UserProfileTEntity.builder()
          .name(profileRequestDto.getProfileName())
          .notificationRecipients(objectMapper.writeValueAsString(profileRequestDto.getRecipients()))
          .userId(userId)
          .deleted(false)
          .createdTime(Timestamp.from(new Date().toInstant()))
          .build();
      userProfileTEntity = userProfileRepository.save(userProfileTEntity);
      profileDto = userProfileTEntity.getProfileDto();
    } catch (Exception e) {
      throw new RuntimeException("Error while creating profile: " + e.getMessage());
    }
    return profileDto;
  }

  public List<ProfileDto> getAllProfile(long userId) {
    List<ProfileDto> profileDtos = new ArrayList<>();

    try {
      profileDtos = userProfileRepository.getAllProfile(userId);

    } catch (Exception e) {
      throw new RuntimeException("Error while fetching profiles: " + e.getMessage());
    }
    return profileDtos;
  }

  public ProfileVehicleResponseDto addVehicleToProfile(long profileId, String vehicleRegistrationNo,
      VehicleRequestDto vehicleRequestDto) {
    ProfileVehicleResponseDto dto = null;
    try {

      Optional<UserProfileTEntity> userProfileT = userProfileRepository.findById(profileId);
      if (userProfileT.isEmpty()) {
        throw new RuntimeException("No such Profile Exist");
      }
      CoreProfileVehicleTr entity = CoreProfileVehicleTr.builder()
          .profileId(profileId)
          .vehicleName(vehicleRequestDto.getVehicleName())
          .vehicleRegistrationNo(vehicleRequestDto.getVehicleRegistrationNumber())
          .userProfile(userProfileT.get())
          .build();
      entity = profileVehicleRepo.save(entity);
      dto = entity.getProfileVehicleDto();

      // saving documents
      List<UserDocumentDto> savedDoc = null;
      CoreProfileVehicleTr finalEntity = entity;
      List<UserDocumentTEntity> userDocumentTEntities = vehicleRequestDto.getDocuments().stream()
          .map(input -> documentService.buildUserDocEntity(
              finalEntity.getProfileId(), vehicleRegistrationNo, finalEntity.getUserProfile(),
              vehicleRequestDto.getRcDto(), input.getS3FileName(), input))
          .toList();

      userDocumentTEntities = userDocumentRepository.saveAll(userDocumentTEntities);
      savedDoc = userDocumentTEntities.stream().map(UserDocumentTEntity::getUserDocDto).toList();
      dto.setDocs(savedDoc);
    } catch (Exception e) {
      throw new RuntimeException("Unable to save Vehicle for Profile", e);
    }

    return dto;
  }

  public List<ProfileVehicleResponseDto> getAllProfileVehicle(long profileId) {
    List<ProfileVehicleResponseDto> responseDtos = new ArrayList<>();
    try {
      List<CoreProfileVehicleTr> entities = profileVehicleRepo.getAllProfileVehicle(profileId,
          false);
      responseDtos = entities.stream()
          .collect(ArrayList::new, (list, entity) -> list.add(entity.getProfileVehicleDto()),
              ArrayList::addAll);
    } catch (Exception e) {
      throw new RuntimeException("Exception during fetch of Profile Vehicles");
    }
    return responseDtos;
  }

  public ProfileDto updateProfile(long userId, long profileId,
      ProfileRequestDto profileRequestDto) {
    Optional<UserProfileTEntity> profileTEntity;
    try {
      profileTEntity = userProfileRepository.findById(profileId);
      if (profileTEntity.isPresent() && profileTEntity.get().getUserId() == userId) {
        UserProfileTEntity entity = profileTEntity.get();
        entity.setName(profileRequestDto.getProfileName());
        entity.setNotificationRecipients(objectMapper.writeValueAsString(profileRequestDto.getRecipients()));
        entity = userProfileRepository.save(entity);
        return entity.getProfileDto();
      } else {
        throw new RuntimeException("No such Profile Exist for the User");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while updating profile: " + e.getMessage());
    }
  }

  public ProfileDto getProfile(long userId, long profileId) {
    Optional<UserProfileTEntity> profileTEntity;
    try {
      profileTEntity=userProfileRepository.findById(profileId);
      if(profileTEntity.isPresent() && profileTEntity.get().getUserId()==userId){
        return profileTEntity.get().getProfileDto();
      }else{
        throw new RuntimeException("No such Profile Exist for the User");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while fetching profile: " + e.getMessage());
    }
  }
}
