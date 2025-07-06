package com.yourstechnology.formbuilder.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourstechnology.formbuilder.service.QuestionService;
import com.yourstechnology.formbuilder.dto.question.AddQuestionRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/forms/{slug}/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService service;
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> addQuestion( @PathVariable String slug, 
                                                            @Valid @RequestBody AddQuestionRequest request) {
        return service.addQuestion(slug, request);
    }
    
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Map<String, String>> removeQuestion(  @PathVariable String slug,
                                                                @PathVariable Long questionId){
    return service.removeQuestion(slug, questionId);
    }
}
