package com.yourstechnology.formbuilder.form;

import org.springframework.web.bind.annotation.RestController;

import com.yourstechnology.formbuilder.dto.FormRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;

    @PostMapping
    public ResponseEntity<?> createForm(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody FormRequest request) {
        return formService.createForm(authHeader, request);
    }

    @GetMapping
    public ResponseEntity<?> getAllForm(@RequestHeader("Authorization") String authHeader) {
        return formService.getAllForms(authHeader);
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<?> detailForm(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable String slug) {
        return formService.detailForm(authHeader, slug);
    }
    
    
}
