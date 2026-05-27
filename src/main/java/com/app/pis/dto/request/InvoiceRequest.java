package com.app.pis.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record InvoiceRequest(
        Integer customerId, // null means retail customer

        @NotNull(message = "User ID is required")
        Integer userId,

        @NotNull(message = "Total amount is required")
        @Min(value = 0, message = "Total amount cannot be negative")
        BigDecimal totalAmound,

        @NotEmpty(message = "Invoice details cannot be empty")
        @Valid
        List<InvoiceDetailRequest> invoiceDetails
) {
}
