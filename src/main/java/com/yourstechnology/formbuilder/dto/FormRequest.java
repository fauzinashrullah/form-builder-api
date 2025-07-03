package com.yourstechnology.formbuilder.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormRequest {
    private String name;
    private String slug;
    private List<String> allowedDomains;
    private String description;
    private Boolean limitOneResponse;
}
