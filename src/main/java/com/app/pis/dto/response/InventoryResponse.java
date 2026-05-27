package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InventoryResponse(
        Long id,
        BigDecimal importPrice,
        LocalDate expirationDate,
        String batchNumber,
        Integer stockQuantity,
        String medicineId,
        Integer supplierId
) {
}
