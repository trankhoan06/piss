package com.app.pis.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CashReceiptRequest(
        @NotNull(message = "Số tiền không được để trống")
        @Min(value = 0, message = "Số tiền phải lớn hơn hoặc bằng 0")
        BigDecimal amount,
        
        @NotBlank(message = "Loại phiếu không được để trống (INCOME hoặc EXPENSE)")
        String type,
        
        String description,
        String referenceType,
        Integer referenceId,
        
        @NotNull(message = "ID người tạo không được để trống")
        Integer userId
) {
}
