package com.yourstechnology.formbuilder.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yourstechnology.formbuilder.dto.form.CreateFormRequest;
import com.yourstechnology.formbuilder.dto.form.FormDetailResponse;
import com.yourstechnology.formbuilder.dto.form.FormQuestionResponse;
import com.yourstechnology.formbuilder.dto.form.FormSummaryResponse;
import com.yourstechnology.formbuilder.entity.AllowedDomain;
import com.yourstechnology.formbuilder.entity.Form;
import com.yourstechnology.formbuilder.exception.DuplicateSlugException;
import com.yourstechnology.formbuilder.exception.ForbiddenException;
import com.yourstechnology.formbuilder.exception.ResourceNotFoundException;
import com.yourstechnology.formbuilder.repository.DomainRepository;
import com.yourstechnology.formbuilder.repository.FormRepository;
import com.yourstechnology.formbuilder.repository.QuestionRepository;
import com.yourstechnology.formbuilder.security.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormService {
    private final JwtTokenService jwtTokenService;
    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final DomainRepository domainRepository;

    public ResponseEntity<Map<String, Object>> createForm(CreateFormRequest request){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Long requestId = jwtTokenService.extractUserId(token);

        if (formRepository.findBySlug(request.getSlug()).isPresent()){
            throw new DuplicateSlugException();
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
        form.setDescription(request.getDescription());
        form.setLimitOneResponse(limitOneResponse);
        form.setCreatorId(requestId);
        formRepository.save(form);

        AllowedDomain allowedDomains = new AllowedDomain();
        allowedDomains.setFormId(form.getId());
        allowedDomains.setDomain(request.getAllowedDomains());
        domainRepository.save(allowedDomains);

        FormSummaryResponse formDto = new FormSummaryResponse(
            form.getId(), 
            form.getName(), 
            form.getSlug(), 
            form.getDescription(), 
            form.getLimitOneResponse(), 
            form.getCreatorId()
            );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Create form success");
        response.put("forms", formDto);
        return ResponseEntity.ok(response);
        
    }

    public ResponseEntity<Map<String, Object>> getAllForms(){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Long creatorId = jwtTokenService.extractUserId(token);

        List<Form> allForm = formRepository.findAllByCreatorId(creatorId);
        List<FormSummaryResponse> list = allForm.stream().map(form -> new 
            FormSummaryResponse(
                form.getId(), 
                form.getName(), 
                form.getSlug(), 
                form.getDescription(), 
                form.getLimitOneResponse(), 
                form.getCreatorId()
                ))
        .collect(Collectors.toList());
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Get all forms success");
        response.put("forms", list);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> detailForm(String slug){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String domain = jwtTokenService.extractDomain(token);

        Form form = formRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Form not found"));
        List<String> allowedDomains = domainRepository.findByFormId(form.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Domains not found"))
            .getDomain();

        if (!allowedDomains.contains(domain)) {
            throw new ForbiddenException();
        }
        
        List<FormQuestionResponse> questions = questionRepository.findAllByFormId(form.getId())
            .stream().map(question -> 
            new FormQuestionResponse(
                question.getId(), 
                question.getFormId(), 
                question.getName(), 
                question.getChoiceType(), 
                question.getChoices(), 
                question.getIsRequired()
                ))
            .collect(Collectors.toList());
        
        FormDetailResponse responseBody = new FormDetailResponse(
            form.getId(), 
            form.getName(), 
            form.getSlug(), 
            form.getDescription(), 
            form.getLimitOneResponse(), 
            form.getCreatorId(), 
            allowedDomains, 
            questions
            );
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Get form success");
        response.put("form", responseBody);
        return ResponseEntity.ok(response);
    }

}