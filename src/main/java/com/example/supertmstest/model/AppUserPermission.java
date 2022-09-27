package com.example.supertmstest.model;

public enum AppUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
