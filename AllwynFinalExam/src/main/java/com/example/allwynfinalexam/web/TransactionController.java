package com.example.allwynfinalexam.web;



import com.example.allwynfinalexam.model.Transaction;
import com.example.allwynfinalexam.repositories.TransactionRepo;
//import com.example.allwynfinalexam.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepository;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "transactionForm";
    }

    @PostMapping("/saveTransaction")
    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction) {
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionCode(UUID.randomUUID().toString());
        transactionRepository.save(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/transactions")
    public String viewTransactions(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);
        return "transactionReport";
    }
}
