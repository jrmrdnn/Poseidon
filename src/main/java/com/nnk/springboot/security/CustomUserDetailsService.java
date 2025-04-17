package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepository
      .findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
    );
  }
}
