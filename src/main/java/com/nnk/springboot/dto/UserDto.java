package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

  @NotBlank(message = "Username is mandatory")
  @Size(max = 125, message = "Username must not exceed 125 characters")
  @Pattern(
    regexp = "^(?=.*[\\p{L}\\p{N}])[\\p{L}\\p{N}\\s\\-.'']+$",
    message = "Username can only contain letters, numbers, dots, underscores, and hyphens"
  )
  private String username;

  @NotBlank(message = "Password is mandatory")
  @Size(
    min = 8,
    max = 125,
    message = "Password must be between 8 and 125 characters"
  )
  @Pattern(
    regexp = "^(?=.*[\\p{L}])(?=.*[\\p{N}])(?=.*[\\p{Lu}])(?=.*[\\p{Ll}])(?=.*[\\p{Punct}])[\\p{L}\\p{N}\\p{Punct}]+$",
    message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
  )
  private String password;

  @NotBlank(message = "Full Name is mandatory")
  @Size(max = 125, message = "Full Name must not exceed 125 characters")
  @Pattern(
    regexp = "^(?=.*[\\p{L}\\p{N}])[\\p{L}\\p{N}\\s\\-.'']+$",
    message = "Full Name can only contain letters and spaces"
  )
  private String fullName;

  @NotBlank(message = "Role is mandatory")
  @Pattern(
    regexp = "^(ROLE_USER|ROLE_ADMIN)$",
    message = "Role must be either ROLE_USER or ROLE_ADMIN"
  )
  private String role;
}
