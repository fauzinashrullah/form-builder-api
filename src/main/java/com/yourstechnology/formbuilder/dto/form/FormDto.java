package com.yourstechnology.formbuilder.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormDto {
    private String name;
    private String slug;
    private String description;
    private Byte limitOneResponse;
    private Long creatorId;
    private Long id;
}
