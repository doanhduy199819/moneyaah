package com.example.moneyaah;

import java.util.List;

public class MyApplication extends android.app.Application {
    private List<String> expenseCategory;
    private List<String> incomeCategory;

    public void setIncomeCategory(List<String> incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public void setExpenseCategory(List<String> expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public List<String> getExpenseCategory() {
        return expenseCategory;
    }

    public List<String> getIncomeCategory() {
        return incomeCategory;
    }

}

