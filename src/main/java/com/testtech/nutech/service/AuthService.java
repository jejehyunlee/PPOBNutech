package com.testtech.nutech.service;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 10/2/2023 14:57
@Last Modified 10/2/2023 14:57
Version 1.0
*/


import com.testtech.nutech.model.request.AuthRequest;
import com.testtech.nutech.model.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<Object> register(AuthRequest authRequest);

    ResponseEntity<Object> login(LoginRequest loginRequestequesta);

}
