package com.yourstechnology.formbuilder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubmitAnswerRequest {
    private Long questionId;
    private String value;
}
