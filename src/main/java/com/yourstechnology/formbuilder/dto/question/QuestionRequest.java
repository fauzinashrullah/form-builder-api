package com.yourstechnology.formbuilder.dto.question;

import java.util.List;

import com.yourstechnology.formbuilder.util.ChoiceType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionRequest {
    private String name;

    @Enumerated(EnumType.STRING)
    private ChoiceType choiceType;

    private List<String> choices;
    private Boolean isRequired;
}
