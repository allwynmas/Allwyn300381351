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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepository;

    @GetMapping("/transactionform")
    public String showForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "transactionForm";
    }

//    @PostMapping("/saveTransaction")
//    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction) {
//        transaction.setTransactionDate(LocalDate.now());
//        transaction.setTransactionCode(UUID.randomUUID().toString());
//        transactionRepository.save(transaction);
//        return "redirect:/transactions";
//    }

//    @PostMapping("/saveTransaction")
//    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction) {
//        transactionRepository.save(transaction); // This will update the transaction if an ID is present
//        return "redirect:/transactions";
//    }


    @PostMapping("/saveTransaction")
    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction) {
        // Check if this is a new transaction (no ID means it's new)
        if (transaction.getId() == null) {
            // Set the transaction date and code only for new transactions
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionCode(UUID.randomUUID().toString());
        }
        transactionRepository.save(transaction); // Save or update the transaction
        return "redirect:/transactions";
    }


    @GetMapping("/transactions")
    public String viewTransactions(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);
        return "transactionReport";
    }

    @GetMapping("/editTransaction")
    public String editTransaction(@RequestParam Long id, Model model) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + id));
        model.addAttribute("transaction", transaction);
        return "transactionForm"; // Reuse the form for editing
    }

//    @GetMapping("/editTransaction")
//    public String editTransaction(@RequestParam Long id, Model model) {
//        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + id));
//        model.addAttribute("transaction", transaction);
//        return "transactionForm"; // Reuse the form for editing
//    }

    @GetMapping("/deleteTransaction")
    public String deleteTransaction(@RequestParam Long id) {
        transactionRepository.deleteById(id);
        return "redirect:/transactions"; // Redirect back to the report page
    }

}
