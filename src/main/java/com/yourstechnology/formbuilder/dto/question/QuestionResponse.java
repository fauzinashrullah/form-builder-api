package com.yourstechnology.formbuilder.dto.question;

import java.util.List;

import com.yourstechnology.formbuilder.util.ChoiceType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private String name;
    private ChoiceType choiceType;
    private Boolean isRequired;
    private List<String> choices;
    private Long formId;
    private Long id;
}
