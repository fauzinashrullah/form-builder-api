package com.yourstechnology.formbuilder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourstechnology.formbuilder.entity.AccessToken;

public interface TokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByToken(String token);
}
