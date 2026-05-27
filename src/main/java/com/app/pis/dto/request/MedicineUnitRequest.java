package com.app.pis.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MedicineUnitRequest (
        @NotNull(message = "Conversion rate is required")
        @Min(value = 1, message = "Conversion rate must be at least 1")
        Integer conversionRate,
        
        String note,
        
        @NotNull(message = "Unit ID is required")
        Integer unitId
) {
}
