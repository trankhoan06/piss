package com.app.pis.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SupplierGroupRequest(
        @NotBlank(message = "Tên nhóm không được để trống")
        String name,
        
        String description
) {
}
