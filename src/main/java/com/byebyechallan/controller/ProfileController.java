package com.byebyechallan.controller;


import com.byebyechallan.dto.ProfileDto;
import com.byebyechallan.dto.ProfileRequestDto;
import com.byebyechallan.dto.ProfileVehicleResponseDto;
import com.byebyechallan.dto.VehicleRequestDto;
import com.byebyechallan.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/{userId}/profile")
@Tag(name = "Profile Controller", description = "Profile API Controller")
public class ProfileController {

  private final UserProfileService profileService;

  public ProfileController(UserProfileService userProfileService) {
    this.profileService = userProfileService;
  }

  @PostMapping()
  @Operation(summary = "Create Profile", description = "Create Profile for a User")
  public ProfileDto createProfile(@PathVariable("userId") long userId,
      @RequestBody ProfileRequestDto profileRequestDto) {
    return profileService.createProfile(userId, profileRequestDto);
  }

  @GetMapping()
  @Operation(summary = "Get all Profile", description = "Get all Profile for a User")
  public List<ProfileDto> getAllProfile(@PathVariable("userId") long userId) {
    return profileService.getAllProfile(userId);
  }

  @PostMapping("/{profileId}/add-vehicle")
  @Operation(summary = "Add Vehicle to Profile", description = "Add Vehicle to a User's Profile")
  public ProfileVehicleResponseDto addVehicleToProfile(@PathVariable("profileId") long profileId,
      @RequestBody VehicleRequestDto vehicleRequestDto) {
    return profileService.addVehicleToProfile(profileId, vehicleRequestDto);
  }

  @GetMapping("/{profileId}/all-vehicle")
  @Operation(summary = "Get all Vehicle for a Profile", description = "Get all Vehicle for a Profile")
  private List<ProfileVehicleResponseDto> getAllProfileVehicle(@PathVariable("profileId") long profileId){
    return profileService.getAllProfileVehicle(profileId);
  }

}
