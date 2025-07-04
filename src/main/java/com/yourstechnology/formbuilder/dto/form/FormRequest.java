package com.yourstechnology.formbuilder.dto.form;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormRequest {
    @NotBlank(message = "The name field is required.")
    private String name;

    @NotBlank(message = "The slug field is required.")
    @Pattern(regexp = "^[a-zA-Z0-9.-]+$", message = "The slug field format wrong.")
    private String slug;

    private List<String> allowedDomains;
    private String description;
    private Boolean limitOneResponse;
}
