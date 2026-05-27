package com.app.pis.service;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public interface RefreshTokenStore {

    void save(String refreshToken, String email);
    String getEmail(String refreshToken);
    void remove(String refreshToken);
    boolean exists(String refreshToken);
}