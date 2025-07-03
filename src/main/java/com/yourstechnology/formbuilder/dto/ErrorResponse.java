package com.yourstechnology.formbuilder.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
}
