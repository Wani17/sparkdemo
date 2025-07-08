package com.example.demo.user.repository;

import com.example.demo.user.domain.RefreshToken;
import com.example.demo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserId(String userId);
}
