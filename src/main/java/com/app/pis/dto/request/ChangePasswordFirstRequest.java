package com.app.pis.dto.request;

public record ChangePasswordFirstRequest(
        String email,
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {
}
