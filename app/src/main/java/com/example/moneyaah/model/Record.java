package com.example.moneyaah.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Record {
    private int id;
    private String date;
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("date", date);
        result.put("type", type);
        result.put("amount", amount);
        result.put("notes", notes);

        return result;
    }
}
