package com.example.moneyaah;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private RelativeLayout mExpenseLayout;
    private TextView mExpenseValue, mYourExpenseValue, mExpenseDuration;
    private Button mNewGoalButton;
    private ImageButton mSaveLimitButton;
    private TextView mBalanceLabel;
    private EditText mLimitEdit;


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

        mNewGoalButton = view.findViewById(R.id.button_new_goal);
        mSaveLimitButton = view.findViewById(R.id.button_save_limit);

        mBalanceLabel = view.findViewById(R.id.label_balance);
        mLimitEdit = view.findViewById(R.id.edit_limit);
    }

    private void setUpEvent() {
        mNewGoalButton.setOnClickListener(this);
        if (UserDData.get().getExpenseGoal() == null) {
            mExpenseLayout.setVisibility(View.GONE);
        } else {
            mExpenseLayout.setVisibility(View.VISIBLE);
            displayGoal(UserDData.get().getExpenseGoal());
        }

        mBalanceLabel.setText(String.valueOf(UserDData.get().getBalance()));
        mLimitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide keyboard when enter
                InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        mSaveLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // send data to userdata
                    double limit = Double.parseDouble(mLimitEdit.getText().toString());
                    Goal goal = new Goal(1, limit, 0);
                    UserDData.get().setTotalGoal(goal);
                    Toast.makeText(getActivity(), "You set alert for limit: $ " + UserDData.get().getTotalGoal().getMoney(), Toast.LENGTH_SHORT).show();
                    mLimitEdit.clearFocus();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid limit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayGoal(@NonNull Goal goal) {
        TextView value, yourValue, duration;
        value = mExpenseValue;
        yourValue = mYourExpenseValue;
        duration = mExpenseDuration;
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
        if (UserDData.get().getExpenseGoal() != null && !UserDData.get().getExpenseGoal().isExpired()) {
            // Alert User
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

            alertDialog.setTitle("Goal already exists");
            alertDialog.setMessage("You are following your goal and almost get it, do you want to" +
                    " override this goal");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), EditGoalActivity.class);
                    startActivityForResult(intent, REQUEST_EDIT);
                }
            });
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", (DialogInterface.OnClickListener) null);
            alertDialog.show();
        }
        else {
            Intent intent = new Intent(getActivity(), EditGoalActivity.class);
            startActivityForResult(intent, REQUEST_EDIT);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        setUpEvent();
    }
}