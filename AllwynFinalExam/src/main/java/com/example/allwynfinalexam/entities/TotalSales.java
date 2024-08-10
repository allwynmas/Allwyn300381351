package com.example.allwynfinalexam.entities;

public class TotalSales {
    private String salesmanName;
    private Double totalAmount;

    public TotalSales(String salesmanName, Double totalAmount) {
        this.salesmanName = salesmanName;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
