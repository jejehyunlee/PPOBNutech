package com.testtech.nutech.service;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 9/22/2023 15:27
@Last Modified 9/22/2023 15:27
Version 1.0
*/


import com.testtech.nutech.entity.Customer;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.CustomerUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CustomerService {
    Customer create(Customer customer);
    Customer getById(String id);
    ResponseEntity<Object> getByEmail(String token);

    ResponeHandler<Object> getBalance (String token);

    ResponseEntity<Object> uploadImage(String token, MultipartFile file);
    List<Customer> getAll(String message, HttpStatus httpStatus);
    ResponseEntity<Object> update(String token, CustomerUpdateRequest customerUpdateRequest);
    Customer deleteById(String id);
}
