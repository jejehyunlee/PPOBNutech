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
import com.testtech.nutech.model.request.CustomerRequest;
import com.testtech.nutech.service.serviceimpl.CustomerServiceImpl;
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

    @PutMapping(
            path = "/profile/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody CustomerRequest customerRequest) {

        String token = request.getHeader("Authorization").substring(7);

        return customerService.update(token, customerRequest);
    }

    @GetMapping(value = "/get/profile")
    public ResponseEntity<Object> customerGetProfile(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        return customerService.getByEmail(token);
    }

    @PostMapping("/profile/uploade/image")
    public ResponseEntity<?> uploadProfileImage(HttpServletRequest request,
                                                @RequestParam("file") MultipartFile file) {
        // Mendapatkan token dari header Authorization
        String token = request.getHeader("Authorization").substring(7);  // Menghapus "Bearer "

        // Memanggil service untuk upload gambar
        return customerService.uploadImage(token, file);
    }

    @GetMapping("profile/balance")
    public ResponeHandler<Object> getBalance(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return customerService.getBalance(token);
    }



}
