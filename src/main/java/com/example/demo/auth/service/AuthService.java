package com.example.demo.auth.service;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.user.domain.User;
import com.example.demo.auth.dto.SignupRequest;
import com.example.demo.auth.repository.AuthRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void signup(SignupRequest dto, HttpServletResponse response) {
        if(authRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        // TODO: JWT implementation
        authRepository.save(User.of(dto, encoder));
    }

    @Transactional
    public void login(LoginRequest dto, HttpServletResponse response) {
        User user = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Incorrect email or password"));

        if(!user.matchPassword(dto.getPassword(), encoder)) {
            throw new IllegalArgumentException("Incorrect email or password");
        }
    }
}
