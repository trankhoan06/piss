package com.app.pis.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExportReceiptDetailRequest(
        @NotNull(message = "Inventory ID is required")
        Long inventoryId,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        String note
) {
}
