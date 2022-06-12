package com.example.moneyaah.classes;

import java.util.Calendar;
import java.util.Date;

public class Goal {
    int type;
    double money;
    Date startDate;
    int duration;
    public static final int EXPENSE = 0;
    public static final int TOTAL = 1;

    public Goal() {
    }

    public Goal(int type, double money, Date date, int duration) {
        this.type = type;
        this.money = money;
        this.startDate = date;
        this.duration = duration;
    }
    public Goal(int type, double money, int duration) {
        this.type = type;
        this.money = money;
        this.startDate = Calendar.getInstance().getTime();
        this.duration = duration;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
