package com.app.pis.dto.request;

public record SupplierRequest(
        String name,
        String email,
        String phone,
        String address
) {
}
