package com.app.pis.dto.response;

import java.math.BigDecimal;

public record InvoiceDetailResponse(
        Integer id,
        String medicineId,
        Integer quantity,
        BigDecimal totalAmound
) {
}
