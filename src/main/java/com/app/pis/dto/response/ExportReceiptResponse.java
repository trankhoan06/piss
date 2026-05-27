package com.app.pis.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ExportReceiptResponse(
        Integer id,
        String issueReason,
        LocalDate date,
        String type,
        String status,
        Integer userId,
        List<ExportReceiptDetailResponse> exportReceiptDetails
) {
}
