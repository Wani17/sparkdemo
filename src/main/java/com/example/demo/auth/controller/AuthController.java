package com.example.demo.auth.controller;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.SignupRequest;
import com.example.demo.auth.service.AuthService;
import com.example.demo.auth.service.TokenService;
import com.example.demo.user.service.RefreshService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

// TODO: try-catch문을 다른 곳에서 진행하도록 해야할 것 같다
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final RefreshService refreshService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest dto, HttpServletResponse response) {
        try {
            authService.signup(dto, response);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest dto, HttpServletResponse response) {
        try {
            authService.login(dto, response);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        tokenService.cancel(response);
        refreshService.removeRefreshToken(request);
        return ResponseEntity.noContent().build();
    }
}
