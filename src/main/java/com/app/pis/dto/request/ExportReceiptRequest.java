package com.app.pis.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ExportReceiptRequest(
        @NotBlank(message = "Type is required (RETURN_SUPPLIER, DESTROY)")
        String type,

        @NotBlank(message = "Issue reason is required")
        String issueReason,

        @NotNull(message = "User ID is required")
        Integer userId,

        @NotEmpty(message = "Export details cannot be empty")
        @Valid
        List<ExportReceiptDetailRequest> details
) {
}
