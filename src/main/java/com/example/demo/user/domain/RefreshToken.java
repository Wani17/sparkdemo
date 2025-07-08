package com.example.demo.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="refresh_token")
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class RefreshToken {
    @Id
    @Column(name="id", length = 36)
    private String userId;

    @Column(name="token", nullable = false)
    private String token;

    @Column(name="expiry", nullable = false)
    private Date expiry;

    public static RefreshToken of(UUID userId, String token, Date expiry) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.userId = userId.toString();
        refreshToken.token = token;
        refreshToken.expiry = expiry;

        return refreshToken;
    }
}
