package com.app.pis.dto.request;

public record  VerifyOtpRequest (
        String email,
        String otp
) {
}
