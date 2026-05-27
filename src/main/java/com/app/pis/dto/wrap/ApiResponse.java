package com.app.pis.dto.wrap;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;


@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private Instant timestamp = Instant.now();
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}