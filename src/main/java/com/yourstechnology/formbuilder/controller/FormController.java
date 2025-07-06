package com.yourstechnology.formbuilder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yourstechnology.formbuilder.dto.form.CreateFormRequest;
import com.yourstechnology.formbuilder.service.FormService;

import java.util.Map;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createForm(@Valid @RequestBody CreateFormRequest request) {
        return service.createForm(request);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllForm() {
        return service.getAllForms();
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<Map<String, Object>> detailForm(@PathVariable String slug) {
        return service.detailForm(slug);
    }
}
