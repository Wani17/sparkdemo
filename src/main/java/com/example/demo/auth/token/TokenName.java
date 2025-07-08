package com.example.demo.auth.token;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.SignupRequest;
import com.example.demo.auth.repository.AuthRepository;
import com.example.demo.auth.service.TokenService;
import com.example.demo.user.domain.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Getter
public class TokenName {
    public static final String ACCESS_COOKIE_NAME = "accessToken";
    public static final String REFRESH_COOKIE_NAME = "refreshToken";
}
