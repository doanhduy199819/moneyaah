package com.example.moneyaah;

public class Category {

    public static final String[] incomeNames = {
            "Salary",
            "Tips"
    };
    public static final String[] expenseNames = {
            "Food",
            "Coffee",
            "Hang out",
            "Dating"
    };

    public static class INCOME {
        public static final int SALARY = 0;
        public static final int TIPS = 1;
        public static String getString(int index) {
            return incomeNames[index];
        };
    }

    public static class EXPENSE {
        public static final int FOOD = 0;
        public static final int COFFEE = 1;
        public static final int HANG_OUT = 2;
        public static final int DATING = 2;
        public static String getString(int index) {
            return expenseNames[index];
        };
    }
}
