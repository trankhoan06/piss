package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record MedicineResponse(
        String id,
        String name,
        UnitResponse baseUnit,
        CategoryResponse category,
        BigDecimal sellingPrice,
        String activeIngredient,
        String manufacturerName,
        String description,
        String status,
        List<MedicineUnitResponse> medicineUnits
) {
}
