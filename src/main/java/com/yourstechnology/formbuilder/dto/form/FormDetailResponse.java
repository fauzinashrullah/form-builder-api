package com.yourstechnology.formbuilder.dto.form;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormDetailResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Byte limitOneResponse;
    private Long creatorId;
    private List<String> allowedDomains;
    private List<FormQuestionResponse> questions;
}

