package com.app.pis.dto.response;

public record CustomerResponse(
        Integer id,
        String fullName,
        String year,
        String phone,
        String email,
        String status,
        Integer customerGroupId
) {
}
