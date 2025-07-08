package com.example.demo.common.exception.domain;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.exception.ErrorCode;

public class AuthException extends BaseException {
    public AuthException(ErrorCode code) {
        super(code);
    }
}
