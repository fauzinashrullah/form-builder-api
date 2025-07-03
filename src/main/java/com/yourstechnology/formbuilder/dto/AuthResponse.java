package com.yourstechnology.formbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private UserResponse user;
}
