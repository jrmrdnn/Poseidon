package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.repository.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserService userService;

  @Nested
  public class TestGetAllUsers {

    @Test
    public void getAllUsers_shouldReturnAllUsers() {
      List<User> users = Arrays.asList(new User(), new User());
      when(userRepository.findAll()).thenReturn(users);

      List<User> result = userService.getAllUsers();

      assertThat(result).hasSize(2);
      verify(userRepository).findAll();
    }

    @Test
    public void getAllUsers_shouldReturnEmptyList_whenNoUsers() {
      when(userRepository.findAll()).thenReturn(Collections.emptyList());

      List<User> result = userService.getAllUsers();

      assertThat(result).isEmpty();
      verify(userRepository).findAll();
    }
  }

  @Nested
  public class TestGetUserById {

    @Test
    public void getUserById_shouldReturnUser_whenIdExists() {
      User user = new User();
      UserDto userDto = new UserDto();

      when(userRepository.findById(1)).thenReturn(Optional.of(user));
      when(userMapper.userToUserDto(user)).thenReturn(userDto);

      UserDto result = userService.getUserById(1);

      assertThat(result).isEqualTo(userDto);
      verify(userRepository).findById(1);
      verify(userMapper).userToUserDto(user);
    }

    @Test
    public void getUserById_shouldThrowException_whenIdDoesNotExist() {
      when(userRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> userService.getUserById(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("User not found");
    }
  }

  @Nested
  public class TestAddUser {

    @Test
    public void addUser_shouldSaveUser() {
      UserDto userDto = new UserDto();
      userDto.setPassword("password");
      User user = new User();

      when(userMapper.userDtoToUser(userDto)).thenReturn(user);

      userService.addUser(userDto);

      verify(userMapper).userDtoToUser(userDto);
      verify(userRepository).save(user);
    }
  }

  @Nested
  public class TestUpdateUser {

    @Test
    public void updateUser_shouldUpdateUser() {
      User existingUser = new User();
      UserDto updatedUserDto = new UserDto();
      updatedUserDto.setPassword("newpassword");

      when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
      when(userMapper.userDtoToUser(1, updatedUserDto)).thenReturn(
        existingUser
      );

      userService.updateUser(1, updatedUserDto);

      verify(userRepository).findById(1);
      verify(userMapper).userDtoToUser(1, updatedUserDto);
      verify(userRepository).save(existingUser);
    }

    @Test
    public void updateUser_shouldThrowException_whenUserNotFound() {
      when(userRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> userService.updateUser(99, new UserDto()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("User not found");
    }
  }

  @Nested
  public class TestDeleteUser {

    @Test
    public void deleteUser_shouldDeleteUser() {
      User user = new User();
      when(userRepository.findById(1)).thenReturn(Optional.of(user));

      userService.deleteUser(1);

      verify(userRepository).findById(1);
      verify(userRepository).delete(user);
    }

    @Test
    public void deleteUser_shouldThrowException_whenUserNotFound() {
      when(userRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> userService.deleteUser(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("User not found");
    }
  }
}
