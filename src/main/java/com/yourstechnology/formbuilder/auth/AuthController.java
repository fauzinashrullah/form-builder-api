package com.yourstechnology.formbuilder.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourstechnology.formbuilder.dto.AuthResponse;
import com.yourstechnology.formbuilder.dto.LoginRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> postMethodName(@RequestHeader("Authorization") String authHeader) {
        ResponseEntity<?> response = service.logout(authHeader);
        return response;
    }
    
    
}
