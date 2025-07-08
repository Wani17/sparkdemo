package com.example.demo.user.domain;

public enum Role {
    USER,
    ADMIN;

    public String getStringName() {
        return "ROLE_" + name();
    }
}
