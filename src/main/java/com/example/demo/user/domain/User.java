package com.example.demo.user.domain;

import com.example.demo.auth.dto.SignupRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.UUID;

@Entity
@Table(name="users")
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 8)
    private String salt;

    @Column(nullable = false, length = 10)
    private String nickname;

    private static String generateSalt(int length) {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[length/2];
        sr.nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public boolean matchPassword(String password, PasswordEncoder encoder) {
        return encoder.matches(password + this.salt, this.password);
    }

    public static User of(SignupRequest dto, PasswordEncoder encoder) {
        String salt = generateSalt(8);
        System.out.println(salt);

        User user = new User();
        user.id = UUID.randomUUID().toString();
        user.email = dto.getEmail();
        user.salt = salt;
        user.password = encoder.encode(dto.getPassword() + salt);
        user.nickname = dto.getNickname();
        return user;
    }
}
