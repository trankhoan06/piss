package com.app.pis.dto.response;

public record ExportReceiptDetailResponse(
        Integer id,
        Long inventoryId,
        Integer quantity,
        String note
) {
}
