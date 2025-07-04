package com.yourstechnology.formbuilder.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtService;
import com.yourstechnology.formbuilder.dto.form.FormDto;
import com.yourstechnology.formbuilder.dto.form.FormRequest;
import com.yourstechnology.formbuilder.dto.form.FormResponse;
import com.yourstechnology.formbuilder.dto.form.GetAllFormResponse;
import com.yourstechnology.formbuilder.dto.question.QuestionDto;
import com.yourstechnology.formbuilder.entity.Form;
import com.yourstechnology.formbuilder.exception.CredentialException;
import com.yourstechnology.formbuilder.exception.ForbiddenAccessException;
import com.yourstechnology.formbuilder.exception.ResourceNotFoundException;
import com.yourstechnology.formbuilder.exception.SlugAlreadyExistsException;
import com.yourstechnology.formbuilder.repository.FormRepository;
import com.yourstechnology.formbuilder.repository.QuestionRepository;
import com.yourstechnology.formbuilder.repository.TokenRepository;
import com.yourstechnology.formbuilder.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormService {
    private final JwtService jwtService;
    private final FormRepository formRepository;
    private final TokenRepository tokenRepository;
    private final QuestionRepository questionRepository;

    public ResponseEntity<?> createForm(String authHeader, FormRequest request){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);

        tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));
        Long requestId = jwtService.extractUserId(token);

        if (formRepository.findBySlug(request.getSlug()).isPresent()){
            throw new SlugAlreadyExistsException();
        }

        Form form = new Form();
        form.setName(request.getName());
        form.setSlug(request.getSlug());
        form.setAllowedDomains(request.getAllowedDomains());
        form.setDescription(request.getDescription());
        form.setLimitOneResponse(request.getLimitOneResponse());
        form.setCreatorId(requestId);
        formRepository.save(form);


        FormDto formDto = new FormDto(form.getName(), form.getSlug(), form.getDescription(), form.getLimitOneResponse(), form.getCreatorId(), form.getId());
        return ResponseEntity.ok(new ApiResponse<>("Create form success", formDto));
        
    }

    public ResponseEntity<?> getAllForms(String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);
        tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));
        
        List<Form> allForm = formRepository.findAll();
        List<FormDto> list = allForm.stream().map(form -> new FormDto(form.getName(), form.getSlug(), form.getDescription(), form.getLimitOneResponse(), form.getCreatorId(), form.getId()))
            .collect(Collectors.toList());
        GetAllFormResponse response = new GetAllFormResponse("Get all forms success", list);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> detailForm(String authHeader, String slug){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CredentialException("Unauthenticated");
        }
        String token = authHeader.substring(7);
        tokenRepository.findByToken(token)
            .orElseThrow(() -> new CredentialException("Unauthenticated"));

        String email = jwtService.extractEmail(token);
        String domain = email.substring(email.indexOf("@") + 1);

        Form form = formRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Form not found"));

        if (!form.getAllowedDomains().contains(domain)) {
            throw new ForbiddenAccessException("Forbidden access");
        }
        
        List<QuestionDto> questions = questionRepository.findAllByFormId(form.getId())
            .stream().map(question -> 
            new QuestionDto(question.getName(), question.getChoiceType(), question.getIsRequired(), question.getChoices(), question.getFormId(), question.getId()))
            .collect(Collectors.toList());
        
        FormResponse response = new FormResponse(form.getId(), form.getName(), form.getSlug(), form.getDescription(), form.getLimitOneResponse(), form.getCreatorId(), form.getAllowedDomains(), questions);
        return ResponseEntity.ok(new ApiResponse<>("Get form success", response));
    }

}