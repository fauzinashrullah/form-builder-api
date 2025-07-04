package com.yourstechnology.formbuilder.dto.question;

import java.util.List;

import com.yourstechnology.formbuilder.util.ChoiceType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionRequest {
    @NotBlank(message = "The name field is required.")
    private String name;

    @NotNull(message = "The name field is required.")
    @Enumerated(EnumType.STRING)
    private ChoiceType choiceType;

    private List<String> choices;
    private Boolean isRequired;
}
