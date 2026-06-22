package com.byebyechallan.repository;

import com.byebyechallan.entity.UserProfileTEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileTEntity, Long> {

  List<UserProfileTEntity> getAllProfile(long userId);
}
