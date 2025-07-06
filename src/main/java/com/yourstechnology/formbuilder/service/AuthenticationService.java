package com.yourstechnology.formbuilder.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.dto.auth.AuthenticationRequest;
import com.yourstechnology.formbuilder.dto.auth.AuthenticationResponse;
import com.yourstechnology.formbuilder.entity.Token;
import com.yourstechnology.formbuilder.entity.User;
import com.yourstechnology.formbuilder.exception.UnauthorizedException;
import com.yourstechnology.formbuilder.repository.TokenRepository;
import com.yourstechnology.formbuilder.repository.UserRepository;
import com.yourstechnology.formbuilder.security.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    
    public ResponseEntity<Map<String, Object>> login(AuthenticationRequest request){
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow((() -> new UnauthorizedException("Email or password incorrect")));
        
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("Email or password incorrect");
        }
        String token = jwtTokenService.generateToken(user);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Login success");
        response.put("user", new AuthenticationResponse(user.getName(), user.getEmail(),token));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> logout(){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Token accessToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new UnauthorizedException("Unauthenticated"));
        tokenRepository.delete(accessToken);

        Map<String ,String> response =new LinkedHashMap<>();
        response.put("Message", "Logout success");

        return ResponseEntity.ok(response);
    }
}