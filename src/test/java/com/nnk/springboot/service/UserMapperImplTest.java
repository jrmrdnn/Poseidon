package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import org.junit.jupiter.api.Test;

class UserMapperImplTest {

  private final UserMapperImpl mapper = new UserMapperImpl();

  @Test
  void userToUserDto_shouldReturnNull_whenUserIsNull() {
    assertNull(mapper.userToUserDto(null));
  }

  @Test
  void userToUserDto_shouldMapCorrectly_whenUserIsValid() {
    User user = new User();
    user.setId(1);
    user.setUsername("User");
    user.setFullname("FullName");
    user.setRole("ROLE_USER");

    UserDto result = mapper.userToUserDto(user);

    assertNotNull(result);
    assertEquals(user.getUsername(), result.getUsername());
    assertEquals(user.getFullname(), result.getFullName());
    assertEquals(user.getRole(), result.getRole());
  }

  @Test
  void userDtoToUser_shouldReturnNull_whenDtoIsNull() {
    assertNull(mapper.userDtoToUser((UserDto) null));
  }

  @Test
  void userDtoToUser_shouldMapCorrectly_whenDtoIsValid() {
    UserDto dto = new UserDto();
    dto.setUsername("User");
    dto.setFullName("FullName");
    dto.setRole("ROLE_USER");

    User result = mapper.userDtoToUser(dto);

    assertNotNull(result);
    assertEquals(dto.getUsername(), result.getUsername());
    assertEquals(dto.getFullName(), result.getFullname());
    assertEquals(dto.getRole(), result.getRole());
  }

  @Test
  void overloadedUserDtoToUser_shouldReturnNull_whenBothIdAndDtoAreNull() {
    assertNull(mapper.userDtoToUser(null, null));
  }

  @Test
  void overloadedUserDtoToUser_shouldSetIdOnly_whenDtoIsNull() {
    Integer id = 1;

    User result = mapper.userDtoToUser(id, null);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertNull(result.getUsername());
    assertNull(result.getFullname());
    assertNull(result.getRole());
  }

  @Test
  void overloadedUserDtoToUser_shouldMapCorrectly_whenIdIsNullButDtoIsValid() {
    UserDto dto = new UserDto();
    dto.setUsername("User");
    dto.setFullName("FullName");
    dto.setRole("ROLE_USER");

    User result = mapper.userDtoToUser(null, dto);

    assertNotNull(result);
    assertNull(result.getId());
    assertEquals(dto.getUsername(), result.getUsername());
    assertEquals(dto.getFullName(), result.getFullname());
    assertEquals(dto.getRole(), result.getRole());
  }

  @Test
  void overloadedUserDtoToUser_shouldMapCorrectly_whenBothIdAndDtoAreValid() {
    Integer id = 1;
    UserDto dto = new UserDto();
    dto.setUsername("User");
    dto.setFullName("FullName");
    dto.setRole("ROLE_USER");

    User result = mapper.userDtoToUser(id, dto);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals(dto.getUsername(), result.getUsername());
    assertEquals(dto.getFullName(), result.getFullname());
    assertEquals(dto.getRole(), result.getRole());
  }
}
