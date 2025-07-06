package com.yourstechnology.formbuilder.dto.response;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubmitFormResponseRequest {
    @NotNull(message = "The answers field is required.")
    private List<SubmitAnswerRequest> answers;
}
