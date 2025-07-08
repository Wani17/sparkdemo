package com.example.demo.user.service;

import com.example.demo.auth.token.TokenName;
import com.example.demo.common.security.jwt.AuthProvider;
import com.example.demo.common.security.jwt.AuthToken;
import com.example.demo.user.domain.RefreshToken;
import com.example.demo.user.repository.RefreshRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefreshService {
    private final RefreshRepository refreshRepository;
    private final AuthProvider authProvider;

    public void saveRefreshToken(UUID userId, AuthToken authToken) {
        String userIdStr = userId.toString();
        String token = authToken.getToken();
        Date expiry;
        try {
            expiry = authToken.getValidTokenClaims().getExpiration();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        refreshRepository.findById(userIdStr)
        .ifPresentOrElse(
pastRefreshToken -> {
                pastRefreshToken.setToken(token);
                pastRefreshToken.setExpiry(expiry);
            },
            () -> {
                RefreshToken refreshToken = RefreshToken.of(userId, token, expiry);
                refreshRepository.save(refreshToken);
            }
        );
    }

    public void removeRefreshToken(HttpServletRequest request) {
        String rawToken = extractRefreshToken(request);
        if(rawToken == null) return;

        AuthToken token;
        try {
            token = authProvider.convert(rawToken);
        } catch (Exception e) {
            return;
        }

        Claims claims;
        try {
            claims = token.getValidTokenClaims();
        } catch (Exception e) {
            log.warn("위조된 토큰 시도: {}", e.getMessage());
            return;
        }

        String userIdStr = claims.get("userId", String.class);
        log.info("🔎 추출된 userId = {}", userIdStr);
        log.info("🔎 클라이언트 전송 토큰 (rawToken) = {}", rawToken);

        refreshRepository.findById(userIdStr)
        .ifPresent(
            stored -> {
                String storedToken = stored.getToken();
                log.info("🔎 DB에 저장된 토큰 = {}", storedToken);
                log.info("🔎 equals 결과 = {}", storedToken.equals(rawToken));

                if(stored.getToken().equals(rawToken)) {
                    log.info("✅ 토큰 일치 → 삭제 수행");
                    refreshRepository.delete(stored);
                }
                else
                    log.info("Wrong Refresh Token Accepted");
            }
        );
    }

    private String extractRefreshToken(HttpServletRequest request) {
        if(request.getCookies() == null) return null;
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(TokenName.REFRESH_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
