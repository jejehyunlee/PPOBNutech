package com.testtech.nutech.service;

import com.testtech.nutech.entity.Transaction;
import com.testtech.nutech.model.request.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface TransactionService {

    ResponseEntity<Object> topUpBalance(String token, TransactionRequest request);

    ResponseEntity<Object> ProcessTransaction(String token, Transaction request);



}
