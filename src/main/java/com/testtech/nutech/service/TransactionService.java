package com.testtech.nutech.service;

import com.testtech.nutech.entity.Transaction;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.TopUpRequest;
import com.testtech.nutech.model.request.TransactionProcessRequest;
import com.testtech.nutech.model.request.TransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface TransactionService {

    ResponseEntity<Object> topUpBalance(String token, TopUpRequest request);

    ResponseEntity<Object> ProcessTransaction(String token, TransactionProcessRequest request);

    Map<String , Object> getTransactionHistory (String token, Integer limit);

}
