package com.yourstechnology.formbuilder.dto.auth;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private String name;
    private String email;
    private String accessToken;
}
