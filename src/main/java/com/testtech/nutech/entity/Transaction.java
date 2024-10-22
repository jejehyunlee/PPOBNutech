package com.testtech.nutech.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "m_transaction")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", unique = true)
    private String invoiceNumber;

    private String serviceCode;

    private String serviceName;

    @Column(name = "transaction_type")
    private String transactionType; // "PAYMENT" atau "TOPUP"

    @Column(name = "amount")
    private Integer top_up_amount; // Jumlah transaksi

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "created_on")
    private LocalDateTime createdOn;


    @JsonInclude(JsonInclude.Include.NON_NULL) // Mengabaikan jika bernilai null
    @ManyToOne // Relasi dengan entitas Service
    @JoinColumn(name = "service_id") // Pastikan service_id ada
    private Services service;

    @JsonInclude(JsonInclude.Include.NON_NULL) // Mengabaikan jika bernilai null
    @ManyToOne // Relasi dengan entitas Service
    @JoinColumn(name = "customer_id") // Pastikan service_id ada
    private Customer customer;


}
