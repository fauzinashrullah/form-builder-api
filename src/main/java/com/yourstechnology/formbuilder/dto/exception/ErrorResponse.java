package com.yourstechnology.formbuilder.dto.exception;

import lombok.*;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
}
