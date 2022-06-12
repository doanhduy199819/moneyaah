package com.example.moneyaah.classes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.moneyaah.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UserDData {
    private double mBalance;
    private RecordData mData;
    private Goal mExpenseGoal;
    private Goal mTotalGoal;
    private static UserDData sGlobInstance;

    private UserDData() {
        mData = RecordData.getInstance();
        initData();
    }
    public static UserDData get() {
        if (sGlobInstance == null) {
            sGlobInstance = new UserDData();
        }
        return sGlobInstance;
    }
    public RecordData getData() {
        return mData;
    }

    public void setData(RecordData data) {
        mData = data;
    }

    public Goal getExpenseGoal() {
        return mExpenseGoal;
    }

    public void setExpenseGoal(Goal expenseGoal) {
        mExpenseGoal = expenseGoal;
    }

    public Goal getTotalGoal() {
        return mTotalGoal;
    }

    public void setTotalGoal(Goal totalGoal) {
        mTotalGoal = totalGoal;
    }

    public double getBalance() {
        return mBalance;
    }

    public void setBalance(double balance) {
        mBalance = balance;
    }

    private void initData() {
        mBalance = 5000; // default
        Calendar calendar = Calendar.getInstance();
//        Date date = new GregorianCalendar(2022, Calendar.JUNE, 11).getTime();
        Date date = new GregorianCalendar(2022, Calendar.JUNE, 2).getTime();

        Goal eGoal = new Goal(0,70, date, 7);

        setExpenseGoal(eGoal);
    }


}
