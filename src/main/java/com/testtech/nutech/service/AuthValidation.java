package com.testtech.nutech.service;


import com.testtech.nutech.model.request.AuthRequest;
import org.springframework.http.ResponseEntity;

public interface AuthValidation {
    ResponseEntity<Object> validateRegisterRequest(AuthRequest authRequest);

}
