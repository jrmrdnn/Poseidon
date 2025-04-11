package com.nnk.springboot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BidListDto {

  @NotBlank(message = "Account is mandatory")
  @Size(max = 30, message = "Account must be less than 30 characters")
  private String account;

  @NotBlank(message = "Type is mandatory")
  @Size(max = 30, message = "Type must be less than 30 characters")
  private String type;

  @NotNull(message = "Bid quantity is mandatory")
  @DecimalMin(
    value = "0.01",
    inclusive = true,
    message = "Bid quantity must be positive"
  )
  @Digits(
    integer = 10,
    fraction = 2,
    message = "Bid quantity must have up to two decimal places"
  )
  private Double quantity;
}
