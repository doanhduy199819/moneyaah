package com.example.moneyaah.classes;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Record implements Comparable<Record> {
    // Some hints about push to main

    public static final int INCOME = 0;
    public static final int EXPENSE = 1;

    int id;
    String date;
    int type;
    double money;
    String category;
    String description;

    public Record() {
    }

    public Record(String date, int type, double money, String category, String description, int id) {
        this.date = date;
        this.money = money;
        this.category = category;
        this.type = type;
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Record rec) {
        return getDate().compareTo(rec.getDate());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("money", money);
        result.put("category", category);
        result.put("type", type);
        result.put("description", description);

        return result;
    }
}
