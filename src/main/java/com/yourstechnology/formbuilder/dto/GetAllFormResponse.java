package com.yourstechnology.formbuilder.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllFormResponse {
    private String message;
    private List<FormDto> forms;
}
