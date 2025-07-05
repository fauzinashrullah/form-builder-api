package com.yourstechnology.formbuilder.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.config.JwtService;
import com.yourstechnology.formbuilder.dto.form.*;
import com.yourstechnology.formbuilder.dto.question.QuestionResponse;
import com.yourstechnology.formbuilder.entity.Form;
import com.yourstechnology.formbuilder.exception.*;
import com.yourstechnology.formbuilder.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormService {
    private final JwtService jwtService;
    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;

    public ResponseEntity<Map<String, Object>> createForm(FormRequest request){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Long requestId = jwtService.extractUserId(token);

        if (formRepository.findBySlug(request.getSlug()).isPresent()){
            throw new SlugAlreadyExistsException();
        }
        Byte limitOneResponse;
        if(request.getLimitOneResponse()){
            limitOneResponse = 1;
        }else{
            limitOneResponse = 0;
        }

        Form form = new Form();
        form.setName(request.getName());
        form.setSlug(request.getSlug());
        form.setAllowedDomains(request.getAllowedDomains());
        form.setDescription(request.getDescription());
        form.setLimitOneResponse(limitOneResponse);
        form.setCreatorId(requestId);
        formRepository.save(form);

        FormDto formDto = new FormDto(form.getName(), form.getSlug(), form.getDescription(), form.getLimitOneResponse(), form.getCreatorId(), form.getId());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Create form success");
        response.put("forms", formDto);
        return ResponseEntity.ok(response);
        
    }

    public ResponseEntity<Map<String, Object>> getAllForms(){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Long creatorId = jwtService.extractUserId(token);

        List<Form> allForm = formRepository.findAllByCreatorId(creatorId);
        List<FormDto> list = allForm.stream().map(form -> new FormDto(form.getName(), form.getSlug(), form.getDescription(), form.getLimitOneResponse(), form.getCreatorId(), form.getId()))
            .collect(Collectors.toList());
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Get all forms success");
        response.put("forms", list);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> detailForm(String slug){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String domain = jwtService.ectractDomain(token);

        Form form = formRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Form not found"));

        if (!form.getAllowedDomains().contains(domain)) {
            throw new ForbiddenAccessException();
        }
        
        List<QuestionResponse> questions = questionRepository.findAllByFormId(form.getId())
            .stream().map(question -> 
            new QuestionResponse(question.getName(), question.getChoiceType(), question.getIsRequired(), question.getChoices(), question.getFormId(), question.getId()))
            .collect(Collectors.toList());
        
        FormResponse responseBody = new FormResponse(form.getId(), form.getName(), form.getSlug(), form.getDescription(), form.getLimitOneResponse(), form.getCreatorId(), form.getAllowedDomains(), questions);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Get form success");
        response.put("forms", responseBody);
        return ResponseEntity.ok(response);
    }

}