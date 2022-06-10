package com.example.moneyaah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class EditGoalActivity extends AppCompatActivity {

    private Switch mTrackExpenseSwitch;
    private Switch mTrackTotalSwitch;
    private LinearLayout mExpenseDetail;
    private LinearLayout mTotalDetail;
    private RadioGroup expenseRadioGroup;
    private RadioGroup totalRadioGroup;
    private EditText expenseEdit;
    private EditText totalEdit;
    private Button setGoalButton;
    private Button cancelButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        setUpUI();
        setUpEvent();
    }

    private void setUpUI() {
        mTrackExpenseSwitch = findViewById(R.id.sw_track_expense);
        mExpenseDetail = findViewById(R.id.expense_detail);
        mExpenseDetail.setVisibility(View.GONE);
        expenseRadioGroup = findViewById(R.id.rgroup_expense);
        expenseEdit = findViewById(R.id.edit_expense_value);

        mTrackTotalSwitch = findViewById(R.id.sw_track_total);
        mTotalDetail = findViewById(R.id.total_detail);
        mTotalDetail.setVisibility(View.GONE);
        totalRadioGroup = findViewById(R.id.rgroup_total);
        totalEdit = findViewById(R.id.edit_total_value);

        setGoalButton = findViewById(R.id.button_set_goal);
        cancelButton = findViewById(R.id.button_cancel);

        // Back Button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpEvent() {
        mTrackExpenseSwitch.setChecked(false);
        mTrackExpenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mExpenseDetail.setVisibility(View.VISIBLE);
                else
                    mExpenseDetail.setVisibility(View.GONE);
            }
        });

        mTrackTotalSwitch.setChecked(false);
        mTrackTotalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mTotalDetail.setVisibility(View.VISIBLE);
                else
                    mTotalDetail.setVisibility(View.GONE);
            }
        });

        // Detail
        expenseRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbutton_expense_week:
                        break;
                    case R.id.rbutton_expense_month:
                        break;
                    default:
                        break;
                }
            }
        });
        totalRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbutton_total_week:
                        break;
                    case R.id.rbutton_total_month:
                        break;
                    default:
                        break;
                }
            }
        });

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set goal here
                double expenseValue, totalValue;
                try {
                    expenseValue = Double.parseDouble(expenseEdit.getText().toString());
                    totalValue = Double.parseDouble(totalEdit.getText().toString());
                }
                catch (Exception e) {
                    Toast.makeText(EditGoalActivity.this, "Invalid Text", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}