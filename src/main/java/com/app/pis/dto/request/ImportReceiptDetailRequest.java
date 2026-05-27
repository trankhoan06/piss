package com.app.pis.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ImportReceiptDetailRequest(
        @NotBlank(message = "Medicine ID is required")
        String medicineId,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        @NotNull(message = "Purchase price is required")
        @Min(value = 0, message = "Purchase price cannot be negative")
        BigDecimal purchasePrice,

        LocalDate expirationDate,
        String batchNumber
) {
}
