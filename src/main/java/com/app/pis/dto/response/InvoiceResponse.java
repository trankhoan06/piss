package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceResponse(
        Integer id,
        LocalDateTime saleDate,
        BigDecimal totalAmound,
        String status,
        Integer customerId,
        Integer userId,
        List<InvoiceDetailResponse> invoiceDetails
) {
}
