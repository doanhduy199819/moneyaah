package com.example.moneyaah.classes;

import java.util.ArrayList;

public class UserDData {
    private RecordData mData;
    private Goal mExpenseGoal;
    private Goal mTotalGoal;
    private static UserDData sGlobInstance;

    private UserDData() {
        mData = RecordData.getInstance();
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

}
