package com.yourstechnology.formbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String email;
    private String accessToken;
}
