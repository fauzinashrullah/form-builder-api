package com.yourstechnology.formbuilder.dto.form;

import java.util.List;

import com.yourstechnology.formbuilder.model.ChoiceType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormQuestionResponse {
    private Long id;
    private Long formId;
    private String name;
    private ChoiceType choiceType;
    private List<String> choices;
    private Byte isRequired;
}
