package com.yourstechnology.formbuilder.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtService;
import com.yourstechnology.formbuilder.dto.auth.*;
import com.yourstechnology.formbuilder.entity.AccessToken;
import com.yourstechnology.formbuilder.entity.User;
import com.yourstechnology.formbuilder.exception.CredentialException;
import com.yourstechnology.formbuilder.repository.TokenRepository;
import com.yourstechnology.formbuilder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    
    public ResponseEntity<Map<String, Object>> login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow((() -> new CredentialException("Email or password incorrect")));
        
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CredentialException("Email or password incorrect");
        }
        String token = jwtService.generateToken(user);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Login success");
        response.put("user", new LoginResponse(user.getName(), user.getEmail(),token));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> logout(){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();

        AccessToken accessToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));
        tokenRepository.delete(accessToken);

        Map<String ,String> response =new LinkedHashMap<>();
        response.put("Message", "Logout success");

        return ResponseEntity.ok(response);
    }
}