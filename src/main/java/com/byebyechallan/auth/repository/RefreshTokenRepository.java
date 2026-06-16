package com.byebyechallan.auth.repository;

import com.byebyechallan.auth.entity.CoreUserMEntity;
import com.byebyechallan.auth.entity.RefreshTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  Optional<RefreshTokenEntity> findByToken(String token);

  @Modifying
  @Transactional
  void deleteByUser(CoreUserMEntity user);

}
