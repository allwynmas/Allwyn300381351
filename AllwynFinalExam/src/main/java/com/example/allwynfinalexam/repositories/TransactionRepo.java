package com.example.allwynfinalexam.repositories;

import com.example.allwynfinalexam.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
