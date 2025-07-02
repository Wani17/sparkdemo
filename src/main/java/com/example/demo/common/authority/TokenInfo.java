package com.example.demo.common.authority;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenInfo {
    private String grantType;
    private String accessToken;
}
