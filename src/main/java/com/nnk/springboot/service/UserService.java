package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  /**
   * Get all users
   * @return List of all users
   */
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Get a user by its ID
   * @param id the ID of the user
   * @return the user with the specified ID
   */
  public UserDto getUserById(Integer id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
    return userMapper.userToUserDto(user);
  }

  /**
   * Add a new user
   * @param userDto the user data
   */
  @Transactional
  public void addUser(UserDto userDto) {
    User user = userMapper.userDtoToUser(userDto);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userRepository.save(user);
  }

  /**
   * Update a user
   * @param id the ID of the user to update
   * @param userDto the updated user data
   */
  @Transactional
  public void updateUser(Integer id, UserDto userDto) {
    userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
    User user = userMapper.userDtoToUser(id, userDto);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userRepository.save(user);
  }

  /**
   * Delete a user
   * @param id the ID of the user to delete
   */
  @Transactional
  public void deleteUser(Integer id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
    userRepository.delete(user);
  }
}
