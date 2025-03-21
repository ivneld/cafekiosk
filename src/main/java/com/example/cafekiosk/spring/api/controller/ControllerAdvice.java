package com.example.cafekiosk.spring.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> bindException(BindException e) {
        return new ResponseEntity<>(
            e.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
            HttpStatus.BAD_REQUEST
        );
    }
}