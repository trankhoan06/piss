package com.app.pis.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record InventoryCheckRequest(
        @NotNull(message = "User ID is required")
        Integer userId,

        String note,

        @NotEmpty(message = "Check details cannot be empty")
        @Valid
        List<InventoryCheckDetailRequest> details
) {
}
