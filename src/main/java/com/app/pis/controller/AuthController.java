package com.app.pis.controller;
import com.app.pis.dto.request.*;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.entity.ForgotPasswordOtp;
import com.app.pis.entity.User;
import com.app.pis.ex.BadRequestException;
import com.app.pis.ex.UnauthorizedException;
import com.app.pis.repository.ForgotPasswordOtpRepository;
import com.app.pis.repository.UserRepository;
import com.app.pis.service.MailService;
import com.app.pis.service.RefreshTokenStore;
import com.app.pis.utils.JwtUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MailService mailService;
    @Autowired
    private ForgotPasswordOtpRepository forgotPasswordOtpRepository;

    @Autowired
    private RefreshTokenStore refreshTokenStore;

    @PostMapping("/login")
    @Transactional(readOnly = true)
    public ResponseEntity<?> login (@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
           throw new UnauthorizedException("Invalid credentials");
        }
        if (user.getFirstLogin()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(
                                HttpStatus.FORBIDDEN.value(), "Password change required",
                                Map.of("firstLogin", true)
                            )
                    );
        }
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);
        refreshTokenStore.save(refreshToken, user.getEmail());
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Login successful",
                Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/change-password/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody SendOtpRequest request) {
        User user = userRepository
                .findUserByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid email"));
        String otp = String.valueOf((int) ((Math.random() * 900000) + 100000));
        ForgotPasswordOtp forgotOtp = new ForgotPasswordOtp();
        forgotOtp.setEmail(user.getEmail());
        forgotOtp.setOtp(otp);
        forgotOtp.setExpiredAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
        forgotPasswordOtpRepository.save(forgotOtp);
        mailService.sendMail(request.email(), "Reset Password", otp);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "OTP sent successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        ForgotPasswordOtp otpEntity =
                forgotPasswordOtpRepository
                        .findByEmailAndOtp(request.email(), request.otp())
                        .orElseThrow(() -> new UnauthorizedException("Invalid OTP"));

        if (otpEntity.getExpiredAt().before(new Date())) {
            throw new UnauthorizedException("OTP has expired");
        }

        User user = userRepository
                .findUserByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid request"));
        String forgotPasswordToken = jwtUtils.generateForgotPasswordToken(user);
        forgotPasswordOtpRepository.delete(otpEntity);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "OTP verified successfully",
                Map.of(
                        "forgotPasswordToken",
                        forgotPasswordToken
                ));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password/first-login")
    public ResponseEntity<?> changeFirstLogin(@RequestBody ChangePasswordFirstRequest request) {
        User user = userRepository
                .findUserByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!Boolean.TRUE.equals(user.getFirstLogin())) {
            throw new BadRequestException("First login password has already been changed");
        }
        if (!request.newPassword().equals(request.confirmNewPassword())) {
            throw new BadRequestException("Password confirmation does not match");
        }
        boolean match = passwordEncoder.matches(request.oldPassword(), user.getPassword());
        if (!match) {
            throw new UnauthorizedException("Invalid old password");
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new BadRequestException("New password must be different from the old password");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setFirstLogin(false);
        userRepository.save(user);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Password changed successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new BadRequestException("Password confirmation does not match");
        }
        JWTClaimsSet claims;
        try {
            claims = jwtUtils.extractClaims(request.forgotPasswordToken());
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid or expired token");
        }
        String type;
        Integer userId;
        try {
            type = claims.getStringClaim("type");
            userId = claims.getIntegerClaim("id");
        } catch (ParseException e) {
            throw new UnauthorizedException("Invalid token");
        }
        if (!"FORGOT_PASSWORD".equals(type)) {
            throw new UnauthorizedException("Invalid token type");
        }
        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new UnauthorizedException(
                                "Invalid token"
                        )
                );
        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException("New password must be different from the old password");
        }
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstLogin(false);
        userRepository.save(user);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Password changed successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BadRequestException("Refresh token is required");
        }
        refreshTokenStore.remove(refreshToken);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Logout successful",
                null
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        if (!refreshTokenStore.exists(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        if (!jwtUtils.validateToken(refreshToken)) {
            refreshTokenStore.remove(refreshToken);
            throw new UnauthorizedException("Refresh token has expired");
        }
        JWTClaimsSet claims;
        try {
            claims = jwtUtils.extractClaims(refreshToken);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        String type;
        String email;
        try {
            type = claims.getStringClaim("type");
            email = claims.getSubject();
        } catch (ParseException e) {
            throw new UnauthorizedException(
                    "Invalid refresh token"
            );
        }
        if (!"REFRESH_TOKEN".equals(type)) {
            throw new UnauthorizedException("Invalid token type");
        }
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        String newAccessToken = jwtUtils.generateAccessToken(user);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Access token refreshed successfully",
                Map.of("accessToken", newAccessToken, "refreshToken", refreshToken
                )
        );
        return ResponseEntity.ok(response);
    }


}
