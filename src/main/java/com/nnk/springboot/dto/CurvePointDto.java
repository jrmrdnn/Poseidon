package com.nnk.springboot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CurvePointDto {

    @NotNull(message = "Curve ID is mandatory")
    @Positive(message = "Curve ID must be positive")
    private Integer curveId;

    @NotNull(message = "Term is mandatory")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "Term must be positive"
    )
    private Double term;

    @NotNull(message = "Value is mandatory")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "Value must be positive"
    )
    private Double value;
}
