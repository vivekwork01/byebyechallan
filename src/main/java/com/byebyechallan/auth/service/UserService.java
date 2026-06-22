package com.byebyechallan.auth.service;

import com.byebyechallan.auth.entity.CoreUserMEntity;
import com.byebyechallan.auth.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
    CoreUserMEntity user = userRepository.findByEmailAndIsDeleted(email, false)
        .orElseThrow(() -> new UsernameNotFoundException("User Not found: " + email));
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
    return new User(user.getEmail(), user.getPassword(), List.of(authority));
  }
}
