package com.app.pis.repository;

import com.app.pis.entity.ForgotPasswordOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordOtpRepository extends JpaRepository<ForgotPasswordOtp, Integer> {
    Optional<ForgotPasswordOtp> findByEmailAndOtp(String email, String otp);
}
