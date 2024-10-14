package com.testtech.nutech.service;

import com.testtech.nutech.entity.Transaction;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.TransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface TransactionService {

    ResponseEntity<Object> topUpBalance(String token, TransactionRequest request);

    ResponseEntity<Object> ProcessTransaction(String token, Transaction request);

    Map<String , Object> getTransactionHistory (String token, Integer limit);

}
