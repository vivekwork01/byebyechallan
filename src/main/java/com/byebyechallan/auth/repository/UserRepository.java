package com.byebyechallan.auth.repository;

import com.byebyechallan.auth.entity.CoreUserMEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CoreUserMEntity, Long> {

  Optional<CoreUserMEntity> findByEmailAndDeleted(String email, Boolean isDeleted);
}
