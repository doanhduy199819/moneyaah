package com.example.moneyaah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moneyaah.classes.Record;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

public class RecordAdapter extends ArrayAdapter {

    // Danh sach tat ca records trong 1 thang (moi row = 1 ngay)
    List<List<Record>> mMonthRecordList;
    String username;

    public RecordAdapter(@NonNull Context context, List<List<Record>> recordList) {
        super(context, R.layout.list_item_record, recordList);
        this.mMonthRecordList = recordList;
        this.username = Helper.getUsername();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_record, parent, false);

        TextView dayInWeekLabel = rowView.findViewById(R.id.label_dayInWeek);
        TextView dayInMonthLabel = rowView.findViewById(R.id.label_dayInMonth);
        TextView monthYearLabel = rowView.findViewById(R.id.label_monthYear);

        TextView dayIncomeLabel = rowView.findViewById(R.id.label_day_income);
        TextView dayExpenseLabel = rowView.findViewById(R.id.label_day_expense);

//        ListView detailView = rowView.findViewById(R.id.list_record_detail);
//        detailView.setVisibility(View.INVISIBLE);

        List<Record> oneDayList = mMonthRecordList.get(position);
        if (oneDayList.size() == 0) return rowView;
        Record firstRec = oneDayList.get(0);

        // Set title label
        Calendar cal = Calendar.getInstance();
//        cal.setTime(firstRec.getDate());
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        String dayWeek = dayNames[cal.get(Calendar.DAY_OF_WEEK)]; // dayWeek[0] = ""
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String year = String.valueOf(cal.get(Calendar.YEAR));

        dayInWeekLabel.setText(dayWeek);
        dayInMonthLabel.setText(day);
        monthYearLabel.setText(month + "/" + year);

        // list view
//        RecordDetailAdapter detailAdapter = new RecordDetailAdapter(getContext(), oneDayList);
//        detailView.setAdapter(detailAdapter);
        LinearLayout detailContainer = (LinearLayout) rowView.findViewById(R.id.details_container);
        for (int i = 0; i < oneDayList.size(); i++) {
            View detailRow = LayoutInflater.from(getContext()).inflate(R.layout.list_item_record_details, parent, false);
            TextView categoryLabel = detailRow.findViewById(R.id.label_category);
            TextView moneyIncomeLabel = detailRow.findViewById(R.id.label_money_income);
            TextView moneyExpenseLabel = detailRow.findViewById(R.id.label_money_expense);

            String category = oneDayList.get(i).getCategory();
            String money = String.valueOf(oneDayList.get(i).getMoney());
            categoryLabel.setText(category);
            if (oneDayList.get(i).getType() == Record.INCOME) {
                moneyIncomeLabel.setTextColor(getContext().getResources().getColor(R.color.incomeColor));
                moneyIncomeLabel.setText("$ " + money);
                moneyExpenseLabel.setVisibility(View.INVISIBLE);
            } else {
                moneyExpenseLabel.setTextColor(getContext().getResources().getColor(R.color.expenseColor));
                moneyExpenseLabel.setText("$ " + money);
                moneyIncomeLabel.setVisibility(View.INVISIBLE);
            }
            detailContainer.addView(detailRow);
        }

        // Income and expense label
        dayIncomeLabel.setText("$ " + String.valueOf(getTotalIncome(position)));
        dayExpenseLabel.setText("$ " + String.valueOf(getTotalExpense(position)));
        return rowView;
    }

    private double getTotalIncome(int index) {
        double sum = 0;
        List<Record> dayRecords = mMonthRecordList.get(index);
        for (int i = 0; i < dayRecords.size(); i++) {
            Record r = dayRecords.get(i);
            if (r.getType() == Record.INCOME)
                sum += r.getMoney();
        }
        return sum;
    }

    private double getTotalExpense(int index) {
        double sum = 0;
        List<Record> dayRecords = mMonthRecordList.get(index);
        for (int i = 0; i < dayRecords.size(); i++) {
            Record r = dayRecords.get(i);
            if (r.getType() == Record.EXPENSE)
                sum += r.getMoney();
        }
        return sum;
    }
}
