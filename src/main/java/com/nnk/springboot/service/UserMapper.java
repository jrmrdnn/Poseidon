package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "username", source = "user.username")
  @Mapping(target = "fullName", source = "user.fullname")
  @Mapping(target = "role", source = "user.role")
  UserDto userToUserDto(User user);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "username", source = "userDto.username")
  @Mapping(target = "fullname", source = "userDto.fullName")
  @Mapping(target = "role", source = "userDto.role")
  User userDtoToUser(UserDto userDto);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "userDto.username")
  @Mapping(target = "fullname", source = "userDto.fullName")
  @Mapping(target = "role", source = "userDto.role")
  User userDtoToUser(Integer id, UserDto userDto);
}
