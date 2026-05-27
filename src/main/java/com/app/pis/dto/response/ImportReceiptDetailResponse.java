package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ImportReceiptDetailResponse(
        Integer id,
        String medicineId,
        Integer quantity,
        BigDecimal purchasePrice,
        LocalDate expirationDate,
        String batchNumber
) {
}
