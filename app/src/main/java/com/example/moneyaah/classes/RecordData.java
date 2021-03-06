package com.example.moneyaah.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecordData {

    private List<Record> mRecList;

    private RecordData() {
        mRecList = new ArrayList<Record>();
    }

    public static RecordData globInstance;

    public static RecordData getInstance() {
        if (globInstance == null) {
            globInstance = new RecordData();
        }
        return globInstance;
    }

    public List<Record> getAllRecords() {
        return mRecList;
    }

    public List<Record> getList(int day, int month) {
        List<Record> res = new ArrayList<>();
        for (int i = 0; i < mRecList.size(); i++) {
            Record rec = mRecList.get(i);
            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(rec.getDate());
            if (month == calendar.get(Calendar.MONTH)
                    && day == calendar.get(Calendar.DAY_OF_MONTH)) {
                res.add(rec);
            }
        }
        return res;
    }

    public List<Double> getListByMonth(int month) {
        List<Record> res = new ArrayList<>();
        for (int i = 0; i < mRecList.size(); i++) {
            Record rec = mRecList.get(i);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(rec.getDate());
            if (month == calendar.get(Calendar.MONTH) && mRecList.get(i).type == 1) {
                res.add(rec);
            }
        }

        double food = 0.0;
        double coffee = 0.0;
        double hangOut = 0.0;
        double dating = 0.0;

        List<Double> listCategory = new ArrayList<>();

        for (int i = 0; i < res.size(); i++) {
            switch (res.get(i).getCategory()) {
                case "Food":
                    food += res.get(i).getMoney();
                    break;
                case "Coffee":
                    coffee += res.get(i).getMoney();
                    break;
                case "Hang out":
                    hangOut += res.get(i).getMoney();
                    break;
                default:
                    dating += res.get(i).getMoney();
                    break;
            }
        }
        listCategory.add(food);
        listCategory.add(coffee);
        listCategory.add(hangOut);
        listCategory.add(dating);

        return listCategory;
    }

    public List<Record> getTodayList() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return getList(day, month);
    }

    public List<List<Record>> getList(int month) {
        List<List<Record>> res = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            List<Record> l = new ArrayList<>();
            res.add(l);
        }
        for (int i = 0; i < mRecList.size(); i++) {
            Record rec = mRecList.get(i);
            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(rec.getDate());
            if (month == calendar.get(Calendar.MONTH)) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                res.get(day).add(rec);
            }
        }
        List<List<Record>> res2 = new ArrayList<>();
        for (List<Record> l : res) {
            if (!l.isEmpty())
                res2.add(l);
        }
        res = res2;
        return res;
    }

    public void add(Record rec) {
        mRecList.add(rec);
        Collections.sort(mRecList);
    }

    public double totalExpense() {
        double sum = 0;
        for (Record r : mRecList) {
            sum += r.type == Record.EXPENSE ? r.getMoney() : 0;
        }
        return sum;
    }

    public double totalExpense(Date startDate, Date endDate) {
        double sum = 0;
        for (Record r : mRecList) {
            if (r.getDate().compareTo(startDate) >= 0
                    && r.getDate().compareTo(endDate) <= 0)
                sum += r.type == Record.EXPENSE ? r.getMoney() : 0;
        }
        return sum;
    }

    public void setmRecList(List<Record> mRecList) {
        this.mRecList = mRecList;
    }
}
