package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
  @Mapping(target = "fullName", source = "user.fullname")
  UserDto userToUserDto(User user);

  @Mapping(target = "fullname", source = "userDto.fullName")
  User userDtoToUser(UserDto userDto);

  @Mapping(target = "fullname", source = "userDto.fullName")
  User userDtoToUser(Integer id, UserDto userDto);
}
