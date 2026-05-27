package com.app.pis.controller;

import com.app.pis.dto.wrap.ApiError;
import com.app.pis.ex.BadRequestException;
import com.app.pis.ex.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ApiError response = new ApiError(
                            HttpStatus.BAD_REQUEST.value(),
                            "Validation failed",
                            Instant.now(),
                            request.getRequestURI(),
                            errors);
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                            HttpStatus.UNAUTHORIZED.value(),
                            ex.getMessage(),
                            Instant.now(),
                            request.getRequestURI(),
                            null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request
    ) {

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Instant.now(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler (Exception exception, HttpServletRequest request) {
        return ResponseEntity.badRequest().build();
    }
}