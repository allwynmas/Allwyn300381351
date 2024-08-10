package com.example.allwynfinalexam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String salesmanName;

    private String item;

    private LocalDate transactionDate;

    private String transactionCode;

    public void setTransactionDate(LocalDate now) {
    }

    public void setTransactionCode(String string) {
    }

    // Getters and Setters
}
