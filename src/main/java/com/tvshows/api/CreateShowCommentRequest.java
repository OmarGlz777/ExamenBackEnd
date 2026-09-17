package com.tvshows.api;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateShowCommentRequest(
        @NotBlank @Size(max = 2_000) String comment,
        @NotNull @DecimalMin("0.0") @DecimalMax("5.0") BigDecimal rating) {
}
