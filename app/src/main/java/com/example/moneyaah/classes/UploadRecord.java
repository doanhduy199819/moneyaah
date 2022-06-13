package com.example.moneyaah.classes;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadRecord {
    public static final int INCOME = 0;
    public static final int EXPENSE = 1;

    String date;
    int type;
    double money;
    String category;
    String description;

    public UploadRecord() {
    }

    public UploadRecord(String date, int type, double money, String category, String description) {
        this.date = date;
        this.money = money;
        this.category = category;
        this.type = type;
        this.description = description;
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
