package com.app.pis.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnitRequest(
        @NotBlank(message = "Unit name is required")
        @Size(max = 50)
        String name,
        
        String note
) {

}
