package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ImportReceiptResponse(
        Integer id,
        LocalDate date,
        BigDecimal totalAmount,
        String type,
        String status,
        Integer userId,
        Integer supplierId,
        List<ImportReceiptDetailResponse> importReceiptDetails
) {
}
