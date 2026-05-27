package com.app.pis.dto.response;

public record InventoryCheckDetailResponse(
        Integer id,
        Long inventoryId,
        Integer inventoryQuantity,
        Integer actualQuantity
) {
}
