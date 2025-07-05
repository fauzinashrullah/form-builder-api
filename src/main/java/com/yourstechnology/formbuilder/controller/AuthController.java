package com.yourstechnology.formbuilder.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourstechnology.formbuilder.dto.auth.LoginRequest;
import com.yourstechnology.formbuilder.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> postMethodName() {
        return service.logout();
    }
    
    
}
