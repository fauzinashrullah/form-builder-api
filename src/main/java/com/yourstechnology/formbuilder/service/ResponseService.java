package com.yourstechnology.formbuilder.service;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtUtil;
import com.yourstechnology.formbuilder.dto.response.AnswerQuestion;
import com.yourstechnology.formbuilder.dto.response.ResponseDto;
import com.yourstechnology.formbuilder.entity.*;
import com.yourstechnology.formbuilder.exception.CredentialException;
import com.yourstechnology.formbuilder.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final FormRepository formRepository;
    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;

    public ResponseEntity<?> submitResponse(String authHeader, String slug, ResponseDto request){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);

        tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));

        Long userId = jwtUtil.extractUserId(token);
        Form form = formRepository.findBySlug(slug)
            .orElseThrow(() -> new CredentialException("Not found"));
        
        Response response = new Response();
        response.setUserId(userId);
        response.setFormId(form.getId());
        responseRepository.save(response);

        List<AnswerQuestion> answerQuestions = request.getAnswers();
        
        for (AnswerQuestion question : answerQuestions) {
            Answer answer = new Answer();
            answer.setResponseId(response.getId());
            answer.setQuestionId(question.getQuestionId());
            answer.setValue(question.getValue());
            answerRepository.save(answer);
        }
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Submit response success");
        return ResponseEntity.ok(responseBody);
    }

    public ResponseEntity<?> getAllResponse(String authHeader, String slug){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);

        tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));

        Long userId = jwtUtil.extractUserId(token);
        Form form = formRepository.findBySlugAndCreatorId(slug, userId)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));

        Response response = responseRepository.findByFormIdAndUserId(form.getId(), userId)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));
        List<Answer> answer = answerRepository.findAllByResponseId(response.getId());
        return ResponseEntity.ok(answer);
    }
}
