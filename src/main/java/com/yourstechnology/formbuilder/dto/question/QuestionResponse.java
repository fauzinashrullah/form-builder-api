package com.yourstechnology.formbuilder.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private String message;
    private QuestionDto question;
}
