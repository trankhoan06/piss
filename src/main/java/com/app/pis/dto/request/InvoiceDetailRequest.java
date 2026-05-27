package com.app.pis.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record InvoiceDetailRequest(
        @NotBlank(message = "Medicine ID is required")
        String medicineId,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        @NotNull(message = "Total amount is required")
        @Min(value = 0, message = "Total amount cannot be negative")
        BigDecimal totalAmound
) {
}
