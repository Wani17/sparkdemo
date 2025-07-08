package com.example.demo.common.security.config;

import com.example.demo.common.security.jwt.AuthProvider;
import io.jsonwebtoken.Jwt;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.access}")
    private long accessExpTime;

    @Value("${jwt.expiration.refresh}")
    private long refreshExpTime;

    @Bean
    public AuthProvider authProvider() {
        return new AuthProvider(secret, accessExpTime, refreshExpTime);
    }
}
