package com.testtech.nutech.service.serviceimpl;

import com.testtech.nutech.entity.Customer;
import com.testtech.nutech.entity.Services;
import com.testtech.nutech.entity.Transaction;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.ServiceRequest;
import com.testtech.nutech.model.request.TransactionRequest;
import com.testtech.nutech.model.response.TransactionResponse;
import com.testtech.nutech.repository.CustomerRepository;
import com.testtech.nutech.repository.ServiceRepository;
import com.testtech.nutech.repository.TransactionRepository;
import com.testtech.nutech.security.JwtUtils;
import com.testtech.nutech.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final JwtUtils jwtUtils;

    private final CustomerRepository customerRepository;

    private final TransactionRepository transactionRepository;

    private final ServiceRepository serviceRepository;

    private final ServiceRequest services;

    @Override
    public ResponseEntity<Object> topUpBalance(String token, TransactionRequest request) {

        String email = jwtUtils.getEmailByToken(token);

        Optional<Customer> customer = customerRepository.findByEmail(email);

        if (customer.isPresent()) {
            // Validasi amount
            if (request.getTop_up_amount() == null || request.getTop_up_amount() <= 0) {
                ResponeHandler<Object> response = new ResponeHandler<>(102, "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0", null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            // Tambah balance ke customer
            Customer currentCustomer = customer.get();

            int currentBalance = Optional.ofNullable(currentCustomer.getBalance()).orElse(0);

            currentCustomer.setBalance(currentBalance + request.getTop_up_amount());


            customerRepository.save(currentCustomer);

                Transaction transaction = new Transaction();
                transaction.setTransactionType("TOP UP");
                transaction.setCreatedOn(request.getCreatedOn());
                transaction.setCreatedOn(LocalDateTime.now());
                transaction.setIdCust(currentCustomer.getIdCustomer());

                // Simpan transaksi ke database
                transactionRepository.saveAndFlush(transaction);



            // Response sukses
            Map<String, Object> data = new HashMap<>();
            data.put("balance", currentCustomer.getBalance());
            ResponeHandler<Object> response = new ResponeHandler<>(0, "Top Up Balance berhasil", data);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> ProcessTransaction(String token, Transaction request) {
        String email = jwtUtils.getEmailByToken(token);

        Optional<Customer> customerOpt = customerRepository.findByEmail(email);

        if (customerOpt.isEmpty()) {
            ResponeHandler<Object> response = new ResponeHandler<>(0, "is pressnt", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerOpt.get();

        // Cari service berdasarkan service_code
        Optional<Services> serviceOpt = serviceRepository.findByServiceCode(request.getServiceCode());
        if (!serviceOpt.isPresent()) {
            ResponeHandler<Object> response = new ResponeHandler<>(102, "Service ataus Layanan tidak ditemukan", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Services service = serviceOpt.get();

        // Cek apakah balance mencukupi
        if (customer.getBalance() < service.getServiceTariff()) {
            ResponeHandler<Object> response = new ResponeHandler<>(104, "saldo tidak cukup", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Kurangi saldo customer
        customer.setBalance(customer.getBalance() - service.getServiceTariff());
        customerRepository.saveAndFlush(customer);

        // Generate nomor invoice
        String invoiceNumber = "INV" + LocalDateTime.now() + "-" + UUID.randomUUID().toString().substring(0, 5);

            // Simpan transaksi
            Transaction transaction = new Transaction();
            transaction.setInvoiceNumber(invoiceNumber);
            transaction.setServiceCode(service.getServiceCode());
            transaction.setServiceName(service.getServiceName());
            transaction.setTransactionType("PAYMENT");
            transaction.setTop_up_amount(service.getServiceTariff());
            transaction.setCreatedOn(LocalDateTime.now());
            transaction.setService(service); // Pastikan ServiceID di-set
            transaction.setIdCust(customer.getIdCustomer());// Pastikan custumerID di-set
            transactionRepository.save(transaction);


        // Kembalikan response sukses
        TransactionResponse transactionResponse = new TransactionResponse(
                invoiceNumber,
                service.getServiceCode(),
                service.getServiceName(),
                "PAYMENT",
                service.getServiceTariff(),
                LocalDateTime.now()
        );

        // Mengembalikan ResponeHandler dengan TransactionResponse
        ResponeHandler<TransactionResponse> response = new ResponeHandler<>(0, "Transaksi berhasil", transactionResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}




