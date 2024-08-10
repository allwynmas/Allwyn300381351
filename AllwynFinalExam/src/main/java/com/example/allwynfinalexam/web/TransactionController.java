/*
Name: Allwyn
Git: https://github.com/allwynmas/Allwyn300381351
 */

package com.example.allwynfinalexam.web;

import com.example.allwynfinalexam.entities.SalesSummary;
import com.example.allwynfinalexam.entities.TotalSales;
import com.example.allwynfinalexam.entities.Transaction;
import com.example.allwynfinalexam.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepository;

    @GetMapping("/transactionform")
    public String showForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "transactionForm";
    }


    @PostMapping("/saveTransaction")
    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction) {
        // Load the existing transaction to ensure fields like transactionDate and transactionCode are preserved
        if (transaction.getId() != null) {
            // Fetch the existing transaction
            Transaction existingTransaction = transactionRepository.findById(transaction.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + transaction.getId()));

            // Preserve the existing transaction code and date if they are not provided in the form
            transaction.setTransactionCode(existingTransaction.getTransactionCode());

            // Preserve the existing transaction date if not provided by the user
            if (transaction.getTransactionDate() == null) {
                transaction.setTransactionDate(existingTransaction.getTransactionDate());
            }
        } else {
            // Set the transaction date and code only for new transactions
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionCode(UUID.randomUUID().toString());
        }

        // Save or update the transaction
        transactionRepository.save(transaction);
        return "redirect:/transactions";
    }


    @GetMapping("/transactions")
    public String viewTransactions(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);

        // Aggregating data for the summary report
        Map<String, SalesSummary> summaryMap = new HashMap<>();
        Map<String, Double> totalSalesMap = new HashMap<>();

        for (Transaction transaction : transactions) {
            String salesmanName = transaction.getSalesmanName();
            SalesSummary summary = summaryMap.getOrDefault(salesmanName, new SalesSummary(salesmanName));

            // Count appliances sold
            switch (transaction.getItem()) {
                case "Washing Machine":
                    summary.setWashingMachines(summary.getWashingMachines() + 1);
                    break;
                case "Refrigerator":
                    summary.setRefrigerators(summary.getRefrigerators() + 1);
                    break;
                case "Music System":
                    summary.setMusicSystems(summary.getMusicSystems() + 1);
                    break;
            }

            summaryMap.put(salesmanName, summary);

            // Sum up total sales amount
            double totalAmount = totalSalesMap.getOrDefault(salesmanName, 0.0) + transaction.getAmount();
            totalSalesMap.put(salesmanName, totalAmount);
        }

        model.addAttribute("salesSummary", summaryMap.values());

        // Prepare the total sales data to be sent to the view
        List<TotalSales> totalSales = new ArrayList<>();
        for (Map.Entry<String, Double> entry : totalSalesMap.entrySet()) {
            totalSales.add(new TotalSales(entry.getKey(), entry.getValue()));
        }
        model.addAttribute("totalSales", totalSales);

        return "transactionReport";
    }

    @GetMapping("/editTransaction")
    public String editTransaction(@RequestParam Long id, Model model) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + id));
        model.addAttribute("transaction", transaction);
        return "transactionForm"; // Reuse the form for editing
    }

    @GetMapping("/deleteTransaction")
    public String deleteTransaction(@RequestParam Long id) {
        transactionRepository.deleteById(id);
        return "redirect:/transactions"; // Redirect back to the report page
    }

}
