package com.app.pis.dto.response;

import java.time.LocalDate;
import java.util.List;

public record InventoryCheckResponse(
        Integer id,
        LocalDate date,
        String status,
        String note,
        Integer userId,
        List<InventoryCheckDetailResponse> details
) {
}
