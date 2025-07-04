package com.yourstechnology.formbuilder.controller;

import org.springframework.web.bind.annotation.RestController;

import com.yourstechnology.formbuilder.dto.form.FormRequest;
import com.yourstechnology.formbuilder.dto.question.QuestionRequest;
import com.yourstechnology.formbuilder.dto.response.SubmitResponseRequest;
import com.yourstechnology.formbuilder.service.FormService;
import com.yourstechnology.formbuilder.service.QuestionService;
import com.yourstechnology.formbuilder.service.ResponseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;
    private final QuestionService questionService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<?> createForm(@RequestHeader("Authorization") String authHeader,
                                        @Valid @RequestBody FormRequest request) {
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
    
    @PostMapping("/{slug}/questions")
    public ResponseEntity<?> addQuestion(   @RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug, 
                                            @Valid @RequestBody QuestionRequest request) {
        return questionService.addQuestion(authHeader, slug, request);
    }
    
    @DeleteMapping("/{slug}/questions/{questionId}")
    public ResponseEntity<?> removeQuestion(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug,
                                            @PathVariable Long questionId){
    return questionService.removeQuestion(authHeader, slug, questionId);
    }

    @PostMapping("/{slug}/responses")
    public ResponseEntity<?> submitResponse(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug,
                                            @Valid @RequestBody SubmitResponseRequest request) {
        return responseService.submitResponse(authHeader, slug, request);
    }
    
    @GetMapping("/{slug}/responses")
    public ResponseEntity<?> getAllResponses(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable String slug) {
        return responseService.getAllResponse(authHeader, slug);
    }
    
}
