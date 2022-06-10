package com.example.moneyaah.classes;

import java.util.Date;

public class Record implements Comparable<Record>{
    // Some hints about push to main

    public static final int INCOME = 0;
    public static final int EXPENSE = 1;

    Date date;
    int type;
    double money;
    String category;
    String description;

    public Record() {
    }

    public Record(Date date, int type, double money, String category, String description) {
        this.date = date;
        this.money = money;
        this.category = category;
        this.type = type;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
