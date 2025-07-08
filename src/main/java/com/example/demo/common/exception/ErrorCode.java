package com.example.demo.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "server error occured"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "invalid input"),

    // Auth
    AUTH_LOGIN_FAILED(HttpStatus.BAD_REQUEST, "email or password do not match"),
    AUTH_SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "duplicate email"),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "invalid token"),
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "expired token"),
    AUTH_UNAUTHORIZED(HttpStatus.FORBIDDEN, "unauthorized");

    private final HttpStatus status;
    private final String message;
}
