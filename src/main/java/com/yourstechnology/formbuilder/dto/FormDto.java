package com.yourstechnology.formbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormDto {
    private String name;
    private String slug;
    private String description;
    private Boolean limitOneResponse;
    private Long creatorId;
    private Long id;
}
