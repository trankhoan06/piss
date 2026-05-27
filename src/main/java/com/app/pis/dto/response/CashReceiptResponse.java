package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashReceiptResponse(
        Integer id,
        LocalDateTime receiptDate,
        BigDecimal amount,
        String type,
        String description,
        String referenceType,
        Integer referenceId,
        Integer userId
) {
}
