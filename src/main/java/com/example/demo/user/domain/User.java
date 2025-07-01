package com.example.demo.user.domain;

import com.example.demo.auth.dto.SignupRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    public static User newUser(SignupRequest dto, PasswordEncoder encoder) {
        User user = new User();
        user.id = UUID.randomUUID().toString();
        user.email = dto.getEmail();
        user.password = encoder.encode(dto.getPassword());
        user.nickname = dto.getNickname();
        return user;
    }
}
