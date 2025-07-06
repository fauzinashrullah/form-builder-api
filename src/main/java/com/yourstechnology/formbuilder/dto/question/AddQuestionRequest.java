package com.yourstechnology.formbuilder.dto.question;

import java.util.List;

import com.yourstechnology.formbuilder.model.ChoiceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddQuestionRequest {
    @NotBlank(message = "The name field is required.")
    private String name;

    @NotNull(message = "The choice type field is required.")
    private ChoiceType choiceType;
    
    private List<String> choices;
    private Boolean isRequired;
}
