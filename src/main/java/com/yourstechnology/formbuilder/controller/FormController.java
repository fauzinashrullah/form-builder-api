package com.yourstechnology.formbuilder.controller;

import org.springframework.web.bind.annotation.RestController;

import com.yourstechnology.formbuilder.dto.form.FormRequest;
import com.yourstechnology.formbuilder.dto.question.QuestionRequest;
import com.yourstechnology.formbuilder.dto.response.SubmitResponseRequest;
import com.yourstechnology.formbuilder.service.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;
    private final QuestionService questionService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createForm(@Valid @RequestBody FormRequest request) {
        return formService.createForm(request);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllForm() {
        return formService.getAllForms();
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<Map<String, Object>> detailForm(@PathVariable String slug) {
        return formService.detailForm(slug);
    }
    
    @PostMapping("/{slug}/questions")
    public ResponseEntity<Map<String, Object>> addQuestion( @PathVariable String slug, 
                                                            @Valid @RequestBody QuestionRequest request) {
        return questionService.addQuestion(slug, request);
    }
    
    @DeleteMapping("/{slug}/questions/{questionId}")
    public ResponseEntity<Map<String, String>> removeQuestion(  @PathVariable String slug,
                                                                @PathVariable Long questionId){
    return questionService.removeQuestion(slug, questionId);
    }

    @PostMapping("/{slug}/responses")
    public ResponseEntity<Map<String, String>> submitResponse(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug,
                                            @Valid @RequestBody SubmitResponseRequest request) {
        return responseService.submitResponse(authHeader, slug, request);
    }
    
    @GetMapping("/{slug}/responses")
    public ResponseEntity<Map<String, Object>> getAllResponses(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug) {
        return responseService.getAllResponse(authHeader, slug);
    }
    
}
