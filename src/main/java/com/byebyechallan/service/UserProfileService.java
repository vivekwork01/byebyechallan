package com.byebyechallan.service;

import com.byebyechallan.dto.ProfileDto;
import com.byebyechallan.dto.ProfileRequestDto;
import com.byebyechallan.entity.UserProfileTEntity;
import com.byebyechallan.repository.UserProfileRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  private final UserProfileRepository userProfileRepository;

  public UserProfileService(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  public ProfileDto createProfile(long userId, ProfileRequestDto profileRequestDto) {
    ProfileDto profileDto = null;

    try {
      UserProfileTEntity userProfileTEntity = UserProfileTEntity.builder()
          .name(profileRequestDto.getProfileName())
          .userId(userId)
          .isDeleted(false)
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
      List<UserProfileTEntity> userProfileTEntities = userProfileRepository.getAllProfile(userId);
      profileDtos = userProfileTEntities.stream().map(UserProfileTEntity::getProfileDto).toList();

    } catch (Exception e) {
      throw new RuntimeException("Error while fetching profiles: " + e.getMessage());
    }
    return profileDtos;
  }
}
