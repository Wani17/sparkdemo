package com.example.demo.common.security.jwt;

import com.example.demo.user.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class AuthToken {
    private final SecretKey key;

    @Getter
    private final String token;

    public AuthToken(SecretKey key, UUID userId, Role role, long tokenExpTime) {
        this.key = key;
        this.token = createToken(userId, role, tokenExpTime);
    }

    AuthToken(SecretKey key, String rawToken) {
        this.key = key;
        this.token = rawToken;
    }

    private String createToken(UUID userId, Role role, long TokenExpTime) {
        Date now = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(now.getTime() + TokenExpTime);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("userId", userId)
                .claim("role", role.getStringName())
                .issuedAt(now)
                .expiration(expiresAt)
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid() {
        try {
            getValidTokenClaims();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired() {
        try {
            getExpiredTokenClaims();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getValidTokenClaims() throws Exception {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.info("JWT expired");
            throw new Exception("JWT expired", e);
        } catch (MalformedJwtException e) {
            log.info("JWT not valid");
            throw new Exception("JWT not valid", e);
        } catch (Exception e) {
            log.info("Exception occured" + e.getMessage());
            throw new Exception("Exception", e);
        }
    }

    public Claims getExpiredTokenClaims() throws Exception {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            log.info("JWT expired");
            return e.getClaims();
        }
        throw new Exception("Not Expired");
    }
}
