package com.app.pis.dto.response;

public record TopMedicineData(
        String medicineId,
        String medicineName,
        long totalQuantitySold
) {
}
