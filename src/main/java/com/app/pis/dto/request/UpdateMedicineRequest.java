package com.app.pis.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record UpdateMedicineRequest(
        @NotBlank(message = "Medicine ID is required")
        String id,
        
        @NotBlank(message = "Medicine name is required")
        String name,
        
        @NotNull(message = "Base unit ID is required")
        Integer baseUnitId,
        
        @NotNull(message = "Category ID is required")
        Integer categoryId,
        
        @NotNull(message = "Selling price is required")
        @Min(value = 0, message = "Price cannot be negative")
        BigDecimal sellingPrice,
        
        String activeIngredient,
        String description,
        String manufacturerName,
        List<MedicineUnitRequest> medicineUnit
) {
}
