package com.yourstechnology.formbuilder.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByToken(String token);
}
