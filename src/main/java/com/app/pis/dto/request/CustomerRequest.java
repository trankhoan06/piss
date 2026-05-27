package com.app.pis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 100)
        String fullName,

        String year,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^(0[0-9]{9,10})$", message = "Phone must be valid Vietnamese format")
        String phone,

        @Email(message = "Invalid email format")
        @Size(max = 100)
        String email,

        String status,
        Integer customerGroupId
) {
}
