package com.yourstechnology.formbuilder.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormSummaryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Byte limitOneResponse;
    private Long creatorId;
}
