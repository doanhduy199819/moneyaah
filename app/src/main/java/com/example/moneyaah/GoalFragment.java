package com.example.moneyaah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moneyaah.classes.Goal;
import com.example.moneyaah.classes.RecordData;
import com.example.moneyaah.classes.UserDData;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_EDIT = 0;

    private String mParam1;
    private String mParam2;
    private RelativeLayout mExpenseLayout, mTotalLayout;
    private TextView mExpenseValue, mYourExpenseValue, mExpenseDuration;
    private TextView mTotalValue, mYourTotalValue, mTotalDuration;
    private Button mEditButton;



    public GoalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalFragment newInstance(String param1, String param2) {
        GoalFragment fragment = new GoalFragment();
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
        View view = inflater.inflate(R.layout.fragment_goal, container, false);

        setUpUI(view);
        setUpEvent();
        return view;
    }

    private void setUpUI(View view) {
        mExpenseLayout = view.findViewById(R.id.expense_track_show);
        mExpenseValue = view.findViewById(R.id.expense_value);
        mYourExpenseValue = view.findViewById(R.id.your_expense_value);
        mExpenseDuration = view.findViewById(R.id.expense_duration_value);

        mTotalLayout = view.findViewById(R.id.total_track_show);
        mTotalValue = view.findViewById(R.id.total_value);
        mYourTotalValue = view.findViewById(R.id.your_total_value);
        mTotalDuration = view.findViewById(R.id.total_duration_value);

        mEditButton = view.findViewById(R.id.button_new_goal);

    }

    private void setUpEvent() {
        mEditButton.setOnClickListener(this);
        if (UserDData.get().getExpenseGoal() == null) {
            mExpenseLayout.setVisibility(View.GONE);
        } else {
            mExpenseLayout.setVisibility(View.VISIBLE);
            displayGoal(UserDData.get().getExpenseGoal());
        }

        if (UserDData.get().getTotalGoal() == null) {
            mTotalLayout.setVisibility(View.GONE);
        } else {
            mTotalLayout.setVisibility(View.VISIBLE);
            displayGoal(UserDData.get().getTotalGoal());
        }
    }

    private void displayGoal(@NonNull Goal goal) {
        TextView value, yourValue, duration;
        if (goal.getType() == Goal.EXPENSE) {
            value = mExpenseValue;
            yourValue = mYourExpenseValue;
            duration = mExpenseDuration;
        }
        else {
            value = mTotalValue;
            yourValue = mYourTotalValue;
            duration = mTotalDuration;
        }
        // value
        value.setText(String.valueOf(goal.getMoney()));

        // duration and your value
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar2.setTime(goal.getStartDate());
        calendar2.add(Calendar.DAY_OF_MONTH, goal.getDuration());
        Date endDate = calendar2.getTime();

        if (endDate.compareTo(today) <= 0) {
            int days = 0;
            duration.setText(days + " days");
            double money = RecordData.getInstance().totalExpense(goal.getStartDate(), endDate);
            yourValue.setText(String.valueOf(money));
        }
        else {

//                long diff = today.getTime() - eGoal.getStartDate().getTime();
//                long days_diff = (diff / (1000*60*60*24)) % 365;
//                mExpenseDuration.setText(String.valueOf(days_diff));
            long diffInMillies = Math.abs(today.getTime() - endDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            duration.setText(String.valueOf(diff));

            double money = RecordData.getInstance().totalExpense(goal.getStartDate(), today);
            if (money < goal.getMoney()) {
                yourValue.setTextColor(getContext().getResources().getColor(R.color.incomeColor));
            }
            else {
                yourValue.setTextColor(getContext().getResources().getColor(R.color.expenseColor));
            }
            yourValue.setText(String.valueOf(money));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), EditGoalActivity.class);
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpEvent();
    }
}