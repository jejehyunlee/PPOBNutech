package com.testtech.nutech.security;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 10/2/2023 13:35
@Last Modified 10/2/2023 13:35
Version 1.0
*/

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtech.nutech.handler.ResponeHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ResponeHandler<Object> commonResponse = ResponeHandler.builder()
                .status(103)
                .message("Token tidak valid dan expired")
                .data(null)
                .build();
        String strCommonResponse = objectMapper.writeValueAsString(commonResponse);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(strCommonResponse);
    }
}
