package com.example.moneyaah;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
        setUpEvent(view);
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

        mEditButton = view.findViewById(R.id.button_edit);

    }

    private void setUpEvent(View view) {
        mEditButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), EditGoalActivity.class);
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT) {
            // Do something here
        }
    }
}