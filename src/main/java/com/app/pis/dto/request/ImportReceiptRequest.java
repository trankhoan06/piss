package com.app.pis.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record ImportReceiptRequest(
        @NotBlank(message = "Type is required (SUPPLIER, CUSTOMER_RETURN, INITIAL)")
        String type,

        @NotNull(message = "Supplier ID is required")
        Integer supplierId,

        @NotNull(message = "User ID is required")
        Integer userId,

        @NotNull(message = "Total amount is required")
        @Min(value = 0, message = "Total amount cannot be negative")
        BigDecimal totalAmount,

        @NotEmpty(message = "Import details cannot be empty")
        @Valid
        List<ImportReceiptDetailRequest> details
) {
}
