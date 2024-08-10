package com.example.allwynfinalexam.repositories;

import com.example.allwynfinalexam.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
