package com.yourstechnology.formbuilder.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtUtil;
import com.yourstechnology.formbuilder.dto.auth.AuthResponse;
import com.yourstechnology.formbuilder.dto.auth.LoginRequest;
import com.yourstechnology.formbuilder.dto.auth.UserResponse;
import com.yourstechnology.formbuilder.entity.AccessToken;
import com.yourstechnology.formbuilder.exception.CredentialException;
import com.yourstechnology.formbuilder.repository.TokenRepository;
import com.yourstechnology.formbuilder.user.User;
import com.yourstechnology.formbuilder.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow((() -> new CredentialException("Email or password incorrect")));
        
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CredentialException("Email or password incorrect");
        }else{
            String token = jwtUtil.generateToken(user);
            return new AuthResponse("Login success", new UserResponse(user.getName(), user.getEmail(),token));
        }
    }

    public ResponseEntity<?> logout(String authHeader){
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        AccessToken accessToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));
        tokenRepository.delete(accessToken);
        Map<String ,String> response =new HashMap<>();
        response.put("Message", "Logout success");
        return ResponseEntity.ok(response);
    }else{
        throw new CredentialException("Unauthenticated");
    }
    }


}