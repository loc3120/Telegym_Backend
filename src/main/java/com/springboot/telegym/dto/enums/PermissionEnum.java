package com.springboot.telegym.dto.enums;

public enum PermissionEnum {

    SELECT ("SELECT"),

    INSERT ("INSERT"),

    UPDATE ("UPDATE"),

    DELETE ("DELETE");

    private final String permissionValue;

    PermissionEnum(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getPermissionValue() {
        return this.permissionValue;
    }
}
