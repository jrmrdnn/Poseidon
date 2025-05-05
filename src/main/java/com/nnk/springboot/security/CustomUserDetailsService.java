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

/**
 * CustomUserDetailsService implements UserDetailsService to load user-specific data.
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Loads user details by username.
   * @param username the username of the user
   * @return UserDetails object containing user information
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    // Fetch the user from the database using the UserRepository
    User user = userRepository
      .findByUsername(username) // Use the repository to find the user by username
      .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Handle the case where the user is not found

    // Create and return a UserDetails object with the user's information
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(), // Use the username as the principal
      user.getPassword(), // Use the password for authentication
      Collections.singletonList(new SimpleGrantedAuthority(user.getRole())) // Use the user's role as the authority
    );
  }
}
