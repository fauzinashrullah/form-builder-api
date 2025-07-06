package com.yourstechnology.formbuilder.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.dto.response.AnswerResponse;
import com.yourstechnology.formbuilder.dto.response.FormResponseDetail;
import com.yourstechnology.formbuilder.dto.response.SubmitAnswerRequest;
import com.yourstechnology.formbuilder.dto.response.SubmitFormResponseRequest;
import com.yourstechnology.formbuilder.dto.response.UserSummary;
import com.yourstechnology.formbuilder.entity.Answer;
import com.yourstechnology.formbuilder.entity.Form;
import com.yourstechnology.formbuilder.entity.Response;
import com.yourstechnology.formbuilder.entity.User;
import com.yourstechnology.formbuilder.exception.ForbiddenException;
import com.yourstechnology.formbuilder.exception.ResourceNotFoundException;
import com.yourstechnology.formbuilder.exception.ValidationException;
import com.yourstechnology.formbuilder.repository.AnswerRepository;
import com.yourstechnology.formbuilder.repository.DomainRepository;
import com.yourstechnology.formbuilder.repository.FormRepository;
import com.yourstechnology.formbuilder.repository.ResponseRepository;
import com.yourstechnology.formbuilder.repository.UserRepository;
import com.yourstechnology.formbuilder.security.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final JwtTokenService jwtTokenService;
    private final FormRepository formRepository;
    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final DomainRepository domainRepository;

    public ResponseEntity<Map<String, String>> submitResponse(String authHeader, String slug, SubmitFormResponseRequest request){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Form form = formRepository.findBySlug(slug)
        .orElseThrow(() -> new ResourceNotFoundException("Form not found"));
        
        List<String> allowedDomains = domainRepository.findByFormId(form.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Domains not found"))
        .getDomain();
        
        String domain = jwtTokenService.extractDomain(token);
        if (!allowedDomains.contains(domain)) {
            throw new ForbiddenException();
        }
        
        Long userId = jwtTokenService.extractUserId(token);
        if (responseRepository.findByFormIdAndUserId(form.getId(), userId).isPresent()){
            throw new ValidationException("You can not submit form twice");
        }
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Response response = new Response();
        response.setUserId(userId);
        response.setFormId(form.getId());
        response.setDate(date);
        responseRepository.save(response);

        List<SubmitAnswerRequest> answerQuestions = request.getAnswers();
        
        for (SubmitAnswerRequest question : answerQuestions) {
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

    public ResponseEntity<Map<String, Object>> getAllResponse(String authHeader, String slug){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (!formRepository.findBySlug(slug).isPresent()){
            throw new ResourceNotFoundException("Form not found");
        }

        Long userId = jwtTokenService.extractUserId(token);
        Form form = formRepository.findBySlugAndCreatorId(slug, userId)
            .orElseThrow(() -> new ForbiddenException());

        List<Response> responses = responseRepository.findAllByFormId(form.getId());
        List<FormResponseDetail> getAllResponses = responses.stream().map(response ->{
            User user = userRepository.findById(response.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            List<Answer> answers = answerRepository.findAllByResponseId(response.getId());
            AnswerResponse answerResponse = new AnswerResponse(
                answers.get(0).getValue(), 
                answers.get(1).getValue(), 
                answers.get(2).getValue(), 
                answers.get(3).getValue()
                );

            UserSummary userDTO = new UserSummary(
                user.getId(), 
                user.getName(), 
                user.getEmail(), 
                user.getEmailVerifiedAt()
                );
            return new FormResponseDetail(response.getDate(), userDTO, answerResponse);
        }).collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Get responses succes");
        response.put("responses", getAllResponses);

        return ResponseEntity.ok(response);
    }
}
