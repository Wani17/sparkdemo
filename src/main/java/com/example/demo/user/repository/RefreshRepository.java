package com.example.demo.user.repository;

import com.example.demo.user.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshToken, String> {

}
