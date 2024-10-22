package com.testtech.nutech.controller;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 9/22/2023 15:39
@Last Modified 9/22/2023 15:39
Version 1.0
*/

import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.CustomerUpdateRequest;
import com.testtech.nutech.service.serviceimpl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @SecurityRequirement(name = "bearer")
    @PutMapping(
            path = "/profile/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody CustomerUpdateRequest customerUpdateRequest) {

        String token = request.getHeader("Authorization").substring(7);

        return customerService.update(token, customerUpdateRequest);
    }

    @SecurityRequirement(name = "bearer")
    @GetMapping(value = "/profile")
    public ResponseEntity<Object> customerGetProfile(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        return customerService.getByEmail(token);
    }

    @SecurityRequirement(name = "bearer")
    @PostMapping(
            path = "/profile/uploade/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE

    )
    public ResponseEntity<?> uploadProfileImage(HttpServletRequest request,
                                                @RequestParam("file") MultipartFile file) {
        // Mendapatkan token dari header Authorization
        String token = request.getHeader("Authorization").substring(7);  // Menghapus "Bearer "

        // Memanggil service untuk upload gambar
        return customerService.uploadImage(token, file);
    }

    @SecurityRequirement(name = "bearer")
    @GetMapping("profile/balance")
    public ResponeHandler<Object> getBalance(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return customerService.getBalance(token);
    }



}
