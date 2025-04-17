package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "Username is mandatory")
  @Column(length = 125)
  private String username;

  @NotBlank(message = "Password is mandatory")
  @Column(length = 125)
  private String password;

  @NotBlank(message = "FullName is mandatory")
  @Column(length = 125)
  private String fullname;

  @NotBlank(message = "Role is mandatory")
  @Column(length = 125)
  private String role;
}
