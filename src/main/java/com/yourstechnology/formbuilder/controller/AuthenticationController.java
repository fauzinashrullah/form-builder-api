package com.yourstechnology.formbuilder.controller;

import com.yourstechnology.formbuilder.dto.auth.AuthenticationRequest;
import com.yourstechnology.formbuilder.service.AuthenticationService;

import java.util.Map;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthenticationRequest request) {
        return service.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> postMethodName() {
        return service.logout();
    }
    
    
}
