package com.testtech.nutech.controller;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 10/3/2023 09:12
@Last Modified 10/3/2023 09:12
Version 1.0
*/

import com.testtech.nutech.model.request.AuthRequest;
import com.testtech.nutech.model.request.LoginRequest;
import com.testtech.nutech.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ppob/api/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<Object> registerCustomer(@RequestBody AuthRequest request) {
       return authService.register(request);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(register);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
            return authService.login(request);
    }



}

