package com.byebyechallan.repository;

import com.byebyechallan.dto.ProfileDto;
import com.byebyechallan.entity.UserProfileTEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileTEntity, Long> {

  List<ProfileDto> getAllProfile(long userId);
}
