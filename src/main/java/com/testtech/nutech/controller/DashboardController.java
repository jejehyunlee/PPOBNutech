package com.testtech.nutech.controller;

import com.testtech.nutech.entity.Customer;
import com.testtech.nutech.model.response.DashboardResponse;
import com.testtech.nutech.service.serviceimpl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("ppob/api")
//@CrossOrigin(origins = "https://localhost:4200", allowCredentials = "true")
public class DashboardController {

    private final CustomerServiceImpl customerService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailLogin = (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken))
                ? authentication.getName()
                : "Anonymous";
        // Buat response object
        DashboardResponse response = DashboardResponse.builder()
                .user(emailLogin)
                .build();

        return ResponseEntity.ok(response);
    }
}
