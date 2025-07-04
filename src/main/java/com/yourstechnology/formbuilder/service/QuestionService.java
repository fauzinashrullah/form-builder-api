package com.yourstechnology.formbuilder.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtService;
import com.yourstechnology.formbuilder.dto.question.QuestionDto;
import com.yourstechnology.formbuilder.dto.question.QuestionRequest;
import com.yourstechnology.formbuilder.dto.question.QuestionResponse;
import com.yourstechnology.formbuilder.entity.Form;
import com.yourstechnology.formbuilder.entity.Question;
import com.yourstechnology.formbuilder.exception.CredentialException;
import com.yourstechnology.formbuilder.exception.ResourceNotFoundException;
import com.yourstechnology.formbuilder.repository.FormRepository;
import com.yourstechnology.formbuilder.repository.QuestionRepository;
import com.yourstechnology.formbuilder.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final FormRepository formRepository;

    public ResponseEntity<?> addQuestion(String authHeader, String slug, QuestionRequest request){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);

        tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));
        
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
        
        QuestionDto dto = new QuestionDto(question.getName(), question.getChoiceType(), question.getIsRequired(), question.getChoices(), form.getId(), question.getId());
        QuestionResponse response = new QuestionResponse("Add question success", dto);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> removeQuestion(String authHeader, String slug, Long questionId){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);

        tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));
        
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));

        questionRepository.delete(question);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Remove question success");
        return ResponseEntity.ok(response);
    }
}
