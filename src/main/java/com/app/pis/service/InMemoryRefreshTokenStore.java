package com.app.pis.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryRefreshTokenStore implements RefreshTokenStore {
    private final Map<String, String> store = new ConcurrentHashMap<>();
    @Override
    public void save(String refreshToken, String email) {
        store.put(refreshToken, email);
    }

    @Override
    public String getEmail(String refreshToken) {
        return store.get(refreshToken);
    }

    @Override
    public void remove(String refreshToken) {
        store.remove(refreshToken);
    }

    @Override
    public boolean exists(String refreshToken) {
        return store.containsKey(refreshToken);
    }
}