package com.example.demo.auth.token;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class TokenName {
    public static final String ACCESS_COOKIE_NAME = "accessToken";
    public static final String REFRESH_COOKIE_NAME = "refreshToken";
}
