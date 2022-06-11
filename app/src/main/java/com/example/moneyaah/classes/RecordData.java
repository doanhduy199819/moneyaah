package com.example.moneyaah.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RecordData {

    private List<Record> mRecList;

    private RecordData() {
        mRecList = new ArrayList<Record>();
        fakeData();
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
        for (int i=0; i<mRecList.size(); i++) {
            Record rec = mRecList.get(i);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(rec.getDate());
            if (month == calendar.get(Calendar.MONTH)
            &&  day == calendar.get(Calendar.DAY_OF_MONTH)) {
                res.add(rec);
            }
        }
        return res;
    }

    public List<Double> getListByMonth(int month) {
        List<Record> res = new ArrayList<>();
        for (int i=0; i<mRecList.size(); i++) {
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

        for(int i=0; i<res.size(); i++){
            switch (res.get(i).getCategory()){
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
        for (int i=0; i<31; i++) {
            List<Record> l = new ArrayList<>();
            res.add(l);
        }
        for (int i=0; i<mRecList.size(); i++) {
            Record rec = mRecList.get(i);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(rec.getDate());
            if (month == calendar.get(Calendar.MONTH)) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                res.get(day).add(rec);
            }
        }
        List<List<Record>> res2 = new ArrayList<>();
        for (List<Record> l: res) {
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
        for (Record r: mRecList) {
            sum += r.type == Record.EXPENSE ? r.getMoney(): 0;
        }
        return sum;
    }
    public double totalExpense(Date startDate, Date endDate) {
        double sum = 0;
        for (Record r: mRecList) {
            if (r.getDate().compareTo(startDate) >= 0
            &&  r.getDate().compareTo(endDate) <= 0)
            sum += r.type == Record.EXPENSE ? r.getMoney(): 0;
        }
        return sum;
    }


    private void fakeData() {

        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 2).getTime(), Record.INCOME, 10, Category.INCOME.getString(0), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 2).getTime(), Record.INCOME, 11, Category.INCOME.getString(0), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 2).getTime(), Record.INCOME, 12, Category.INCOME.getString(0), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 2).getTime(), Record.INCOME, 13, Category.INCOME.getString(0), "Nothing"));

        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 3).getTime(), Record.INCOME, 14, Category.INCOME.getString(0), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 3).getTime(), Record.EXPENSE, 15, Category.EXPENSE.getString(0), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 3).getTime(), Record.EXPENSE, 16, Category.EXPENSE.getString(0), "Nothing"));

        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 4).getTime(), Record.INCOME, 14, Category.INCOME.getString(0), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 4).getTime(), Record.EXPENSE, 15, Category.EXPENSE.getString(0), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.JUNE, 4).getTime(), Record.EXPENSE, 16, Category.EXPENSE.getString(0), "Nothing"));

        add(new Record(new GregorianCalendar(2022, Calendar.MAY, 23).getTime(), Record.INCOME, 14, Category.INCOME.getString(Category.INCOME.TIPS), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.MAY, 24).getTime(), Record.EXPENSE, 15, Category.EXPENSE.getString(Category.EXPENSE.DATING), "Nothing"));
        add(new Record(new GregorianCalendar(2022, Calendar.MAY, 25).getTime(), Record.EXPENSE, 16, Category.EXPENSE.getString(Category.EXPENSE.FOOD), "Nothing"));
    }
}
