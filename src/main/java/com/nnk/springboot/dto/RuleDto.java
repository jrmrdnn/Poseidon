package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RuleDto {

  @NotBlank(message = "Name is mandatory")
  @Size(max = 125, message = "Name must not exceed 125 characters")
  private String name;

  @NotBlank(message = "Description is mandatory")
  @Size(max = 125, message = "Description must not exceed 125 characters")
  private String description;

  @NotBlank(message = "Json is mandatory")
  @Size(max = 125, message = "Json must not exceed 125 characters")
  private String json;

  @NotBlank(message = "Template is mandatory")
  @Size(max = 512, message = "Template must not exceed 512 characters")
  private String template;

  @NotBlank(message = "SQL String is mandatory")
  @Size(max = 125, message = "SQL String must not exceed 125 characters")
  private String sqlStr;

  @NotBlank(message = "SQL Part is mandatory")
  @Size(max = 125, message = "SQL Part must not exceed 125 characters")
  private String sqlPart;
}
