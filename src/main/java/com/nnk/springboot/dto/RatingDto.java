package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatingDto {

  @NotBlank(message = "Moodys rating is mandatory")
  @Size(max = 125, message = "Moody's rating must not exceed 125 characters")
  private String moodys;

  @NotBlank(message = "Sand rating is mandatory")
  @Size(max = 125, message = "Sand rating must not exceed 125 characters")
  private String sand;

  @NotBlank(message = "Fitch rating is mandatory")
  @Size(max = 125, message = "Fitch rating must not exceed 125 characters")
  private String fitch;

  @NotNull(message = "Order number is mandatory")
  @Positive(message = "Order number must be positive")
  private Integer order;
}
