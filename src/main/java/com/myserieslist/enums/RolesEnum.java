package com.myserieslist.enums;

public enum RolesEnum {

    USER("user"),
    ADMIN("admin");

    private String key;

    RolesEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
