package com.yourstechnology.formbuilder.exception;

public class ValidationException extends RuntimeException {
    public ValidationException (String message){
        super(message);
    }
}

