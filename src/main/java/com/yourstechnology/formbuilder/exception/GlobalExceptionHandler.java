package com.yourstechnology.formbuilder.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<Map<String, String>> handleCredentialException(CredentialException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex){
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<Map<String, String>> handleForbiddenAccess() {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Forbidden access");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex){
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
            Map.of("message", "Method not allowed")
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> bodyErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();

            bodyErrors.computeIfAbsent(field, key -> new ArrayList<>()).add(message);
        });
        return buildErrorResponse(bodyErrors);
    }

    @ExceptionHandler(SlugAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleSlugExists(SlugAlreadyExistsException ex){
        List<String> list = new ArrayList<>();
        list.add("The slug has already been taken.");
        Map<String, List<String>> body = new HashMap<>();
        body.put("slug", list);
        return buildErrorResponse(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleBadJson(HttpMessageNotReadableException ex) {
        List<String> list = new ArrayList<>();
        list.add("The allowed domains must be an array.");
        Map<String, List<String>> body = new HashMap<>();
        body.put("allowed_domains", list);

        return buildErrorResponse(body);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(Map<String, List<String>> errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Invalid field");
        body.put("errors", errors);
        return ResponseEntity.status(442).body(body);
    }
}
