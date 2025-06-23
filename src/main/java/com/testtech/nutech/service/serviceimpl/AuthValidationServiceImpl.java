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
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        // Email kosong
        if (email == null || email.trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ResponeHandler<>(101, "Email tidak boleh kosong", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Format email invalid
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return new ResponseEntity<>(
                    new ResponeHandler<>(102, "Format email tidak sesuai", email),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Email sudah terdaftar
        if (userCredentialRepository.existsByEmail(email)) {
            return new ResponseEntity<>(
                    new ResponeHandler<>(103, "User sudah terdaftar", email),
                    HttpStatus.CONFLICT
            );
        }

        // Password kosong / kurang dari 8 karakter
        if (password == null || password.length() < 8) {
            return new ResponseEntity<>(
                    new ResponeHandler<>(105, "Password minimal 8 karakter", null),
                    HttpStatus.BAD_REQUEST // ✅ diperbaiki
            );
        }

        return null; // ✅ Validasi lolos
    }


}
