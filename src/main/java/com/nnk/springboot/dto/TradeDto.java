package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TradeDto {

  @NotBlank(message = "Account is mandatory")
  @Size(max = 30, message = "Account must not exceed 30 characters")
  private String account;

  @NotBlank(message = "Type is mandatory")
  @Size(max = 30, message = "Type must not exceed 30 characters")
  private String type;

  @NotNull(message = "Buy Quantity is mandatory")
  @Positive(message = "Buy Quantity must be positive")
  private Double quantity;
}
