package com.app.pis.dto.request;

import java.math.BigDecimal;
import java.util.List;

public record MedicineRequest (
        String id,
        String name,
        Integer baseUnitId,
        Integer categoryId,
        BigDecimal sellingPrice,
        String activeIngredient,
        String description,
        String manufacturerName,
        List<MedicineUnitRequest> medicineUnit
) {

}
