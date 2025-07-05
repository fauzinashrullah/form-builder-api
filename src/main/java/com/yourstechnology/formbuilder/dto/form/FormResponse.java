package com.yourstechnology.formbuilder.dto.form;

import java.util.List;

import com.yourstechnology.formbuilder.dto.question.QuestionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Byte limitOneResponse;
    private Long creatorId;
    private List<String> allowedDomains;
    private List<QuestionResponse> questions;
}

