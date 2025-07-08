package com.example.demo.common.security.jwt;

import com.example.demo.user.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
public class AuthProvider {
    private final SecretKey key;

    @Getter
    private final long accessTokExp;

    @Getter
    private final long refreshTokExp;

    public AuthProvider(String secret, long accessTokExp, long refreshTokExp) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.accessTokExp = accessTokExp;
        this.refreshTokExp = refreshTokExp;
    }

    public AuthToken createAccessToken(UUID userId, Role role) {
        return new AuthToken(key, userId, role, accessTokExp);
    }

    public AuthToken createRefreshToken(UUID userId, Role role) {
        return new AuthToken(key, userId, role, refreshTokExp);
    }

    public Authentication getAuthentication(String rawToken) {
        AuthToken authToken = new AuthToken(key, rawToken);
        if(!authToken.isTokenValid()) return null;

        Claims claims;
        try {
            claims = authToken.getValidTokenClaims();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String userIdStr = claims.get("userId", String.class);
        String roleStr = claims.get("role", String.class);

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + roleStr));

        return new UsernamePasswordAuthenticationToken(userIdStr, rawToken, authorities);
    }

    public AuthToken convert(String token) {
        AuthToken authToken = new AuthToken(key, token);

        if(!authToken.isTokenValid())
            throw new IllegalArgumentException("Invalid token");

        return authToken;
    }
}
