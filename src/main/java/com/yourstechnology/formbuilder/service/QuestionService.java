package com.yourstechnology.formbuilder.service;


import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtService;
import com.yourstechnology.formbuilder.dto.question.*;
import com.yourstechnology.formbuilder.entity.Form;
import com.yourstechnology.formbuilder.entity.Question;
import com.yourstechnology.formbuilder.exception.CredentialException;
import com.yourstechnology.formbuilder.exception.ForbiddenAccessException;
import com.yourstechnology.formbuilder.exception.ResourceNotFoundException;
import com.yourstechnology.formbuilder.repository.FormRepository;
import com.yourstechnology.formbuilder.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final JwtService jwtService;
    private final FormRepository formRepository;

    public ResponseEntity<Map<String, Object>> addQuestion(String slug, QuestionRequest request){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        
        if (!formRepository.findBySlug(slug).isPresent()){
            throw new ResourceNotFoundException("Form not Found");
        }
        Long creatorId = jwtService.extractUserId(token);
        Form form = formRepository.findBySlugAndCreatorId(slug, creatorId)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));

        Question question = new Question();
        question.setName(request.getName());
        question.setChoiceType(request.getChoiceType());
        question.setChoices(request.getChoices());
        question.setFormId(form.getId());
        question.setIsRequired(request.getIsRequired());
        questionRepository.save(question);
        
        QuestionResponse questionResponse = new QuestionResponse(question.getName(), question.getChoiceType(), question.getIsRequired(), question.getChoices(), form.getId(), question.getId());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Add question success");
        response.put("question", questionResponse);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> removeQuestion(String slug, Long questionId){ 
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        
        if (!formRepository.findBySlug(slug).isPresent()){
            throw new ResourceNotFoundException("Form not Found");
        }
        Long creatorId = jwtService.extractUserId(token);
        if (!questionRepository.findById(questionId).isPresent()){
            throw new ResourceNotFoundException("Question not found");
        }

        Form form = formRepository.findBySlugAndCreatorId(slug, creatorId)
        .orElseThrow(() -> new ForbiddenAccessException());
        
        Question question = questionRepository.findByIdAndFormId(questionId, form.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        questionRepository.delete(question);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Remove question success");
        return ResponseEntity.ok(response);
    }
}
