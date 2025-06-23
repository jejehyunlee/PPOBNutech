package com.testtech.nutech.handler;

/*
Created By IntelliJ IDEA 2025.1.2 (Ultimate Edition)
Build #IU-251.26094.121, built on June 3, 2025
@Author JEJE a.k.a Jefri S
Java Developer
Created On 6/23/2025 5:29 AM
@Last Modified 6/23/2025 5:29 AM
Version 1.0
*/

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponeHandler<Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .filter(e -> e.getDefaultMessage() != null)
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (first, second) -> first // avoid duplicate keys
                ));

        String message = fieldErrors.isEmpty() ?
                "Validation failed" :
                String.join("; ", fieldErrors.values());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponeHandler<>(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        fieldErrors
                ));
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponeHandler<Object>> handleAll(Exception ex) {
        return new ResponseEntity<>(
                new ResponeHandler<>(103, "Terjadi kesalahan internal", null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}