package com.app.pis.dto.response;


public record SupplierResponse(
        Integer id,
        String name,
        String email,
        String phone,
        String address
) {
}
