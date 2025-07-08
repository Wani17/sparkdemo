package com.example.demo.auth.service;

import com.example.demo.common.security.jwt.AuthProvider;
import com.example.demo.common.security.jwt.AuthToken;
import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.RefreshService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.demo.auth.token.TokenName.ACCESS_COOKIE_NAME;
import static com.example.demo.auth.token.TokenName.REFRESH_COOKIE_NAME;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AuthProvider authProvider;
    private final RefreshService refreshService;

    public void issue(User user, HttpServletResponse response) {
        UUID userId = UUID.fromString(user.getId());
        Role role = user.getRole();

        AuthToken accessToken = authProvider.createAccessToken(userId, role);
        AuthToken refreshToken = authProvider.createRefreshToken(userId, role);

        refreshService.saveRefreshToken(userId, refreshToken);

        response.addCookie(createCookie(ACCESS_COOKIE_NAME, accessToken.getToken(), authProvider.getAccessTokExp()));
        response.addCookie(createCookie(REFRESH_COOKIE_NAME, refreshToken.getToken(), authProvider.getRefreshTokExp()));
    }

    private Cookie createCookie(String name, String value, long maxAgeMillis) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge((int) maxAgeMillis / 1000);
        return cookie;
    }

    public void cancel(HttpServletResponse response) {
        response.addCookie(createCookie(ACCESS_COOKIE_NAME, null, 0));
        response.addCookie(createCookie(REFRESH_COOKIE_NAME, null, 0));
    }
}
