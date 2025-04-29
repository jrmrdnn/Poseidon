package com.nnk.springboot.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CustomUserDetailsService userDetailsService;

  private User testUser;

  @BeforeEach
  public void setUp() {
    testUser = new User();
    testUser.setUsername("User");
    testUser.setPassword("Password");
    testUser.setRole("ROLE_USER");
  }

  @Test
  public void loadUserByUsername_WhenUserExists_ReturnsUserDetails() {
    when(userRepository.findByUsername("User")).thenReturn(
      Optional.of(testUser)
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername("User");

    assertNotNull(userDetails);
    assertEquals("User", userDetails.getUsername());
    assertEquals("Password", userDetails.getPassword());
    assertTrue(
      userDetails
        .getAuthorities()
        .stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))
    );
    verify(userRepository, times(1)).findByUsername("User");
  }

  @Test
  public void loadUserByUsername_WhenUserNotFound_ThrowsException() {
    String username = "User2";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    Exception exception = assertThrows(UsernameNotFoundException.class, () ->
      userDetailsService.loadUserByUsername(username)
    );

    assertEquals("User not found", exception.getMessage());
    verify(userRepository, times(1)).findByUsername(username);
  }
}
