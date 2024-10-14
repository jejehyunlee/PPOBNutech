package com.testtech.nutech.repository;


import com.testtech.nutech.entity.Customer;
import com.testtech.nutech.entity.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {


    List<Transaction> findByCustomerEmailOrderByCreatedOnDesc(String email);

    List<Transaction> findByCustomerEmailOrderByCreatedOnDesc(String email, PageRequest pageRequest);
}

