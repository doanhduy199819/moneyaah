package com.example.moneyaah;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.RecordData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthHistoryFragment extends Fragment {

    int month;
    ListView mListMonthRecord;
    TextView mIncome, mExpense, mTotal;
    TextView mMonthLabel;

    // Data
    List<List<Record>> listRecords;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonthHistoryFragment() {
        // Required empty public constructor
        this.month = Calendar.getInstance().get(Calendar.MONTH);
        listRecords = RecordData.getInstance().getList(month);
    }

    public MonthHistoryFragment(int month) {
        // Required empty public constructor
        this.month = month;
        listRecords = RecordData.getInstance().getList(month);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthHistoryFragment newInstance(String param1, String param2) {
        MonthHistoryFragment fragment = new MonthHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_month_history, container, false);
        if (listRecords.isEmpty()) {
            mMonthLabel = view.findViewById(R.id.label_month);
            mMonthLabel.setText("Tháng " + (month + 1));
            return view;
        }

        setUpUI(view);
        setUpEvents(view);
        return view;
    }

    private void setUpUI(View view) {
        mMonthLabel = view.findViewById(R.id.label_month);
        mIncome = view.findViewById(R.id.label_income_value);
        mExpense = view.findViewById(R.id.label_expense_value);
        mTotal = view.findViewById(R.id.label_total_value);

        mListMonthRecord = view.findViewById(R.id.list_month_record);

    }

    private void setUpEvents(View view) {
        listRecords = RecordData.getInstance().getList(month);

        // Labels
        mMonthLabel.setText("Tháng " + String.valueOf(month + 1));
        mIncome.setText("$ " + String.valueOf(getTotalIncome()));
        mExpense.setText("$ " + String.valueOf(getToTalExpense()));
        mTotal.setText("$ " + String.valueOf(getTotalIncome() - getToTalExpense()));

        // List View

        RecordAdapter monthHistoryAdapter = new RecordAdapter(getActivity(), listRecords);
        mListMonthRecord.setAdapter(monthHistoryAdapter);
    }

    private double getTotalIncome() {
        double sum = 0;
        for (int i = 0; i < listRecords.size(); i++) {
            List<Record> oneDayList = listRecords.get(i);
            for (int j = 0; j < oneDayList.size(); j++) {
                sum += (oneDayList.get(j).getType() == Record.INCOME) ? oneDayList.get(j).getMoney() : 0;
            }
        }
        return sum;
    }

    private double getToTalExpense() {
        double sum = 0;
        for (int i = 0; i < listRecords.size(); i++) {
            List<Record> oneDayList = listRecords.get(i);
            for (int j = 0; j < oneDayList.size(); j++) {
                sum += (oneDayList.get(j).getType() == Record.EXPENSE) ? oneDayList.get(j).getMoney() : 0;
            }
        }
        return sum;
    }
}