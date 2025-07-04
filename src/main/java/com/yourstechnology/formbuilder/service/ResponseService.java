package com.yourstechnology.formbuilder.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtService;
import com.yourstechnology.formbuilder.dto.response.*;
import com.yourstechnology.formbuilder.entity.*;
import com.yourstechnology.formbuilder.exception.*;
import com.yourstechnology.formbuilder.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final JwtService jwtUtil;
    private final TokenRepository tokenRepository;
    private final FormRepository formRepository;
    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> submitResponse(String authHeader, String slug, SubmitResponseRequest request){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);

        if (!tokenRepository.findByToken(token).isPresent()){
            throw new CredentialException("Unauthenticated");
        }

        Long userId = jwtUtil.extractUserId(token);
        Form form = formRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Form not found"));
        
        String email = jwtUtil.extractEmail(token);
        String domain = email.substring(email.indexOf("@") + 1);
        if (!form.getAllowedDomains().contains(domain)) {
            throw new ForbiddenAccessException("Forbidden access");
        }
        
        if (responseRepository.findByFormIdAndUserId(form.getId(), userId).isPresent()){
            throw new ValidationException("You can not submit form twice");
        }
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Response response = new Response();
        response.setUserId(userId);
        response.setFormId(form.getId());
        response.setDate(date);
        responseRepository.save(response);

        List<AnswerRequest> answerQuestions = request.getAnswers();
        
        for (AnswerRequest question : answerQuestions) {
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

        if (!formRepository.findBySlug(slug).isPresent()){
            throw new ResourceNotFoundException("Form not found");
        }

        Long userId = jwtUtil.extractUserId(token);
        Form form = formRepository.findBySlugAndCreatorId(slug, userId)
            .orElseThrow(() -> new ForbiddenAccessException("Forbidden access"));

        List<Response> responses = responseRepository.findAllByFormId(form.getId());
        List<GetResponse> getAllResponses = responses.stream().map(response ->{
            User user = userRepository.findById(response.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            List<Answer> answers = answerRepository.findAllByResponseId(response.getId());
            AnswerResponse answerResponse = new AnswerResponse(answers.get(0).getValue(), answers.get(1).getValue(), answers.get(2).getValue(), answers.get(3).getValue());
            UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getEmailVerifiedAt());
            return new GetResponse(response.getDate(), userDTO, answerResponse);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(getAllResponses);
    }
}
