package com.example.moneyaah;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
    RecordAdapter monthHistoryAdapter;

    // Data
    List<List<Record>> listRecords;

    List<Record> dayTwo = new ArrayList<Record>();
    List<Record> dayThree = new ArrayList<Record>();
    List<Record> dayFour = new ArrayList<Record>();
    List<Record> dayFive = new ArrayList<Record>();
    List<Record> daySix = new ArrayList<Record>();

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
            mMonthLabel.setText("Tháng " + (month+1));
            return view;
        }

        setUpUI(view);
        setUpEvents(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    public void updateUI() {
        if (monthHistoryAdapter == null) return;
        monthHistoryAdapter.clear();
        listRecords = RecordData.getInstance().getList(month);
        if (listRecords != null){
            for (Object obj : listRecords) {
                monthHistoryAdapter.insert(obj, monthHistoryAdapter.getCount());
            }
        }
        monthHistoryAdapter.notifyDataSetChanged();
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
        mMonthLabel.setText("Tháng " + String.valueOf(month+1));
        mIncome.setText("$ " + String.valueOf(getTotalIncome()));
        mExpense.setText("$ " + String.valueOf(getToTalExpense()));
        mTotal.setText("$ " + String.valueOf(getTotalIncome() - getToTalExpense()));

        // List View

        monthHistoryAdapter = new RecordAdapter(getActivity(), listRecords);
        mListMonthRecord.setAdapter(monthHistoryAdapter);
        mListMonthRecord.setOnScrollListener(new AbsListView.OnScrollListener() {
//            boolean isShown = true;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

//                if (scrollState == SCROLL_STATE_TOUCH_SCROLL){
////                    btn.animate().translationYBy(350);
//                    MonthHistoryFragment.this.hideFloattingButton();
//                } else{
////                    btn.animate().cancel();
//                    MonthHistoryFragment.this.showFloattingButton();
//                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    MonthHistoryFragment.this.showFloattingButton();
                }
                else {
                    MonthHistoryFragment.this.hideFloattingButton();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem < 2) {
//
//                    MonthHistoryFragment.this.hideFloattingButton();
//                }else {
//                    MonthHistoryFragment.this.showFloattingButton();
//                }
            }
        });
    }

    public void showFloattingButton() {
        HistoryWalletFragment parentFrag = ((HistoryWalletFragment)MonthHistoryFragment.this.getParentFragment());
        parentFrag.showFloattingButton();
    }
    public void hideFloattingButton() {
        HistoryWalletFragment parentFrag = ((HistoryWalletFragment)MonthHistoryFragment.this.getParentFragment());
        parentFrag.hideFloattingButton();
    }

    private double getTotalIncome() {
        double sum = 0;
        for (int i=0; i<listRecords.size(); i++) {
            List<Record> oneDayList = listRecords.get(i);
            for (int j=0; j<oneDayList.size(); j++) {
                sum += (oneDayList.get(j).getType() == Record.INCOME)? oneDayList.get(j).getMoney(): 0;
            }
        }
        return sum;
    }
    private double getToTalExpense() {
        double sum = 0;
        for (int i=0; i<listRecords.size(); i++) {
            List<Record> oneDayList = listRecords.get(i);
            for (int j=0; j<oneDayList.size(); j++) {
                sum += (oneDayList.get(j).getType() == Record.EXPENSE)? oneDayList.get(j).getMoney(): 0;
            }
        }
        return sum;
    }
}