package com.app.pis.entity.enums;

public enum UserRole {

    ADMIN ("ADMIN"),
    STAFF ("STAFF")
    ;

    public final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }


}
