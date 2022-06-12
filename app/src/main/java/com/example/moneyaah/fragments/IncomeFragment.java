package com.example.moneyaah.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.moneyaah.Helper;
import com.example.moneyaah.MyApplication;
import com.example.moneyaah.R;
import com.example.moneyaah.classes.Category;
import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.UserDData;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IncomeFragment extends Fragment {

    String[] items = new String[]{"Trả thêm giờ", "Tiền lương", "Tiền cấp", "Tiền thưởng", "Khác"};

    private EditText selectDate;
    private EditText money;
    private EditText description;
    private Button btnSave;
    private TextView edtMoney;
    private TextView edtDescription;
    private Spinner dropdown;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_income, container, false);
        Spinner dropdown = inflate.findViewById(R.id.spinner_iCategory);
        selectDate = inflate.findViewById(R.id.edt_iSelect_date);
        money = inflate.findViewById(R.id.edt_iMoney);
        description = inflate.findViewById(R.id.edt_iDescription);
        btnSave = inflate.findViewById(R.id.btn_iSave);

        initUi(inflate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(selectDate);
            }
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

        LocalDateTime now = LocalDateTime.now();
        selectDate.setText(dtf.format(now).toString());
        List<String> category;
        if (((MyApplication) getActivity().getApplication()).getIncomeCategory().size() == 0) {
            category = Arrays.stream(Category.expenseNames)
                    .collect(Collectors.toCollection(ArrayList::new));
        } else category = ((MyApplication) getActivity().getApplication()).getIncomeCategory();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflate.getContext(), android.R.layout.simple_spinner_dropdown_item, category);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long dtStart = Date.parse(selectDate.getText().toString());
                Date date = new Date(dtStart);
                Record r = new Record(String.valueOf(dtStart), Record.EXPENSE, Double.parseDouble(money.getText().toString()), dropdown.getSelectedItem().toString(), description.getText().toString(), 1);

                Log.i("Infor", r.getDate() + " " + r.getCategory() + " " + r.getMoney() + " " + r.getDescription());
                addNewRecord();
                UserDData.get().getData().add(r);
                getActivity().finish();
            }
        });


        return inflate;
    }


    private void initUi(View inflate) {
        selectDate = inflate.findViewById(R.id.edt_iSelect_date);
        btnSave = inflate.findViewById(R.id.btn_iSave);
        edtMoney = inflate.findViewById(R.id.edt_iMoney);
        edtDescription = inflate.findViewById(R.id.edt_iDescription);
        dropdown = inflate.findViewById(R.id.spinner_iCategory);
    }

    private void addNewRecord() {
        double recordAmount = Double.parseDouble(edtMoney.getText().toString());
        Record newRecord = new Record(selectDate.getText().toString(), Record.INCOME, recordAmount, dropdown.getSelectedItem().toString(), edtDescription.getText().toString(), UserDData.get().getData().getAllRecords().size() + 1);
        Map<String, Object> recordUpdate = newRecord.toMap();
        recordUpdate.put(String.valueOf(UserDData.get().getData().getAllRecords().size() + 1), newRecord);
        Helper.updateObject("User/" + Helper.getUsername(requireActivity()) + "/Records/", newRecord);
        String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        recordAmount = 1000000;
        Helper.updateNumber("User/" + Helper.getUsername(requireActivity()) + "/Amount", recordAmount);
    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(IncomeFragment.this.getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(IncomeFragment.this.getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}