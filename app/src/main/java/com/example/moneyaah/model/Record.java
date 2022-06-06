package com.example.moneyaah.model;

import androidx.annotation.NonNull;

public class Record {
    private int id;
    private String type;
    private double amount;
    private String notes;

    public Record(int id, String type, double amount, String notes) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getNotes() {
        return notes;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Record{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
