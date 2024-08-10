package com.example.allwynfinalexam.entities;

public class SalesSummary {
    private String salesmanName;
    private int washingMachines;
    private int refrigerators;
    private int musicSystems;

    public SalesSummary(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    // Getters and Setters
    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public int getWashingMachines() {
        return washingMachines;
    }

    public void setWashingMachines(int washingMachines) {
        this.washingMachines = washingMachines;
    }

    public int getRefrigerators() {
        return refrigerators;
    }

    public void setRefrigerators(int refrigerators) {
        this.refrigerators = refrigerators;
    }

    public int getMusicSystems() {
        return musicSystems;
    }

    public void setMusicSystems(int musicSystems) {
        this.musicSystems = musicSystems;
    }
}
