package com.app.pis.dto.request;

public record ChangePasswordRequest(
        String forgotPasswordToken,
        String password,
        String confirmPassword
) {
}
