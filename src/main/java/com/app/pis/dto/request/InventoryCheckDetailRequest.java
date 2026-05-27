package com.app.pis.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryCheckDetailRequest(
        @NotNull(message = "Inventory ID is required")
        Long inventoryId,

        @NotNull(message = "Actual quantity is required")
        @Min(value = 0, message = "Actual quantity cannot be negative")
        Integer actualQuantity
) {
}
