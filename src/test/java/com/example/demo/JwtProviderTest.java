package com.example.demo;

import com.example.demo.common.security.jwt.AuthProvider;
import com.example.demo.user.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtProviderTest {
    @Autowired
    private AuthProvider jwtProvider;

    @Test
    void testJwtProvider() {
        Long userId = 123L;
//        String token = jwtProvider.createAccessToken(userId, Role.USER);

//        System.out.println("Token: " + token);
    }
}
