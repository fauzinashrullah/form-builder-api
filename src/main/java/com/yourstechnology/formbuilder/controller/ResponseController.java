package com.yourstechnology.formbuilder.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourstechnology.formbuilder.service.ResponseService;
import com.yourstechnology.formbuilder.dto.response.SubmitFormResponseRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/forms/{slug}/responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService service;
    
    @PostMapping
    public ResponseEntity<Map<String, String>> submitResponse(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug,
                                            @Valid @RequestBody SubmitFormResponseRequest request) {
        return service.submitResponse(authHeader, slug, request);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllResponses(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug) {
        return service.getAllResponse(authHeader, slug);
    }
}
