package com.app.pis.dto.response;

public record MedicineUnitResponse(
        Integer id,
        UnitResponse unit,
        Integer conversionRate,
        String note
) {
}
