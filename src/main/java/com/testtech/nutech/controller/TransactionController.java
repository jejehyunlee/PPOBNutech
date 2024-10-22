package com.testtech.nutech.controller;

import com.testtech.nutech.entity.Transaction;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.TopUpRequest;
import com.testtech.nutech.model.request.TransactionProcessRequest;
import com.testtech.nutech.model.request.TransactionRequest;
import com.testtech.nutech.repository.ServiceRepository;
import com.testtech.nutech.service.serviceimpl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/customer")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    private final ServiceRepository serviceRepository;

    @SecurityRequirement(name = "bearer")
    @PostMapping("/topup")
    public ResponseEntity<?> topUpBalance(HttpServletRequest request, @RequestBody TopUpRequest transactionRequest) {
        // Extract email dari JWT Token
        String getToken = request.getHeader("Authorization").substring(7);
        return transactionService.topUpBalance(getToken , transactionRequest);
    }


    @SecurityRequirement(name = "bearer")
    @PostMapping("/transaction")
    public ResponseEntity<?> processTransaction(HttpServletRequest request, @RequestBody TransactionProcessRequest transactionRequest) {
        // Extract email dari JWT Token
        String getToken = request.getHeader("Authorization").substring(7);
        try {
            return transactionService.ProcessTransaction(getToken , transactionRequest );

        }catch (Exception e){
           e.printStackTrace();
           e.getMessage();
        }
        return null;
    }

    @SecurityRequirement(name = "bearer")
    @GetMapping("/history")
    public ResponseEntity<ResponeHandler<Object>> getTransactionHistory(
            @RequestParam(value = "limit", required = false) Integer limit, HttpServletRequest request) {

        // Panggil service untuk mengambil data transaksi
        String getToken = request.getHeader("Authorization").substring(7);
        Map<String, Object> historyData = transactionService.getTransactionHistory(getToken, limit);

        // Response sukses
        ResponeHandler<Object> response = new ResponeHandler<>(0, "Transaction History Berhasil", historyData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
