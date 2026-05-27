package com.app.pis.entity.enums;

public enum UserStatus {
    ACTIVE("active"),
    LOCKED("locked");

    public final String status;
    UserStatus(String status) {
        this.status = status;
    }
}
