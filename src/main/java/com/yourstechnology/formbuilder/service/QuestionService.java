package com.yourstechnology.formbuilder.service;


import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.dto.question.AddQuestionRequest;
import com.yourstechnology.formbuilder.dto.question.QuestionResponse;
import com.yourstechnology.formbuilder.entity.Form;
import com.yourstechnology.formbuilder.entity.Question;
import com.yourstechnology.formbuilder.exception.ForbiddenException;
import com.yourstechnology.formbuilder.exception.ResourceNotFoundException;
import com.yourstechnology.formbuilder.repository.FormRepository;
import com.yourstechnology.formbuilder.repository.QuestionRepository;
import com.yourstechnology.formbuilder.security.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final JwtTokenService jwtTokenService;
    private final FormRepository formRepository;

    public ResponseEntity<Map<String, Object>> addQuestion(String slug, AddQuestionRequest request){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        
        if (!formRepository.findBySlug(slug).isPresent()){
            throw new ResourceNotFoundException("Form not Found");
        }
        Long creatorId = jwtTokenService.extractUserId(token);
        Form form = formRepository.findBySlugAndCreatorId(slug, creatorId)
            .orElseThrow(() -> new ForbiddenException());

        Byte isRequired = (byte) (request.getIsRequired() ? 1 : 0);

        Question question = new Question();
        question.setName(request.getName());
        question.setChoiceType(request.getChoiceType());
        question.setChoices(request.getChoices());
        question.setFormId(form.getId());
        question.setIsRequired(isRequired);
        questionRepository.save(question);
        
        Boolean responseIsRequired = isRequired == 1 ? true : false;
        QuestionResponse questionResponse = new QuestionResponse(
            question.getName(), 
            question.getChoiceType(), 
            responseIsRequired, 
            question.getChoices(), 
            form.getId(), 
            question.getId()
            );
            
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
        Long creatorId = jwtTokenService.extractUserId(token);
        if (!questionRepository.findById(questionId).isPresent()){
            throw new ResourceNotFoundException("Question not found");
        }

        Form form = formRepository.findBySlugAndCreatorId(slug, creatorId)
        .orElseThrow(() -> new ForbiddenException());
        
        Question question = questionRepository.findByIdAndFormId(questionId, form.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        questionRepository.delete(question);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Remove question success");
        return ResponseEntity.ok(response);
    }
}
