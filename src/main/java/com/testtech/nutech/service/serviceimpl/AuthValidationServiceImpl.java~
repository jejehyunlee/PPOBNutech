package com.testtech.nutech.service.serviceimpl;

import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.AuthRequest;
import com.testtech.nutech.repository.UserCredentialRepository;
import com.testtech.nutech.service.AuthValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthValidationServiceImpl implements AuthValidation {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public ResponseEntity<Object> validateRegisterRequest(AuthRequest authRequest) {
        if (userCredentialRepository.existsByEmail(authRequest.getEmail())){
            ResponeHandler<Object> response = new ResponeHandler<>(103, "User sudah terdaftar", authRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }


        if (authRequest.getEmail().trim().isEmpty()) {
            ResponeHandler<Object> response = new ResponeHandler<>(101, "Email tidak boleh kosong", authRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (!authRequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
            ResponeHandler<Object> response = new ResponeHandler<>(102, "Email tidak seusai format", authRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (authRequest.getPassword() == null || authRequest.getPassword().length() < 8) {
            ResponeHandler<Object> response = new ResponeHandler<>(105, "Password Minimal 8 Karakter", authRequest.getPassword());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

}
