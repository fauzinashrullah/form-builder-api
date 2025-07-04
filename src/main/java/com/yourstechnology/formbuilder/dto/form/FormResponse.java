package com.yourstechnology.formbuilder.dto.form;

import java.util.List;

import com.yourstechnology.formbuilder.dto.question.QuestionDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Boolean limitOneResponse;
    private Long creatorId;
    private List<String> allowedDomains;
    private List<QuestionDto> questions;
}

