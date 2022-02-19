package com.springboot.telegym.dto.enums;

public enum RoleEnum {

    ROLE_STAFF ("ROLE_STAFF"),

    ROLE_ADMIN ("ROLE_ADMIN");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
