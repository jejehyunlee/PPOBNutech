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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "https://localhost:4200", allowCredentials = "true")
@RequestMapping("/ppob/api/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<Object> registerCustomer(@Valid @RequestBody AuthRequest request) {
       return authService.register(request);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
            return authService.login(request, response);
    }

    @PostMapping("logout") 
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return authService.logout(response);
    }
}

