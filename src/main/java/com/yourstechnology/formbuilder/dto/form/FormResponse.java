package com.yourstechnology.formbuilder.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormResponse {
    private String message;
    private FormDto form;
}
