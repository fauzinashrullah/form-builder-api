package com.yourstechnology.formbuilder.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.entity.AccessToken;
import com.yourstechnology.formbuilder.entity.User;
import com.yourstechnology.formbuilder.repository.TokenRepository;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private final TokenRepository tokenRepository;

    public Key getSignInKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user){
        String jwt = Jwts.builder()
        .setSubject(user.getEmail())
        .claim("id", user.getId())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(),SignatureAlgorithm.HS256)
        .compact();

        AccessToken token = new AccessToken();
        token.setToken(jwt);
        tokenRepository.save(token);
        return jwt;

    }

    public Date getExpirationDate(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
    }
    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.get("id", Long.class);
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public String ectractDomain(String token){
        String email = extractEmail(token);
        return email.substring(email.indexOf("@") + 1);
    }

    public Boolean isTokenValid(String token){
        return tokenRepository.findByToken(token).isPresent();
    }

}
