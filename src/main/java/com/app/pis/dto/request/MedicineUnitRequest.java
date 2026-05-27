package com.app.pis.dto.request;

public record MedicineUnitRequest (
        Integer conversionRate,
        String note,
        Integer unitId
) {
}
