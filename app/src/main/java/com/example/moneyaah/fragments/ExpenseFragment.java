package com.example.moneyaah.fragments;

import static com.example.moneyaah.activity.MainActivity.amount;
import static com.example.moneyaah.activity.MainActivity.records;

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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.moneyaah.Helper;
import com.example.moneyaah.R;
import com.example.moneyaah.classes.Category;
import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.UserDData;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class ExpenseFragment extends Fragment {

    String[] items = new String[]{"Ăn uống", "Giải trí", "Tự thân phát triển", "Giao thông vận tải", "Sở thích", "Sinh hoạt",
            "Áo quần", "Làm đẹp", "Sức khỏe", "Giáo dục", "Sự kiện", "Khác"};

    private EditText selectDate;
    private Button btnSave;
    private TextView edtMoney;
    private TextView edtDescription;
    private Spinner dropdown;
    private EditText money;
    private EditText description;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_expense_fragent, container, false);
        initUI(inflate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Spinner dropdown = inflate.findViewById(R.id.spinner_eCategory);
        selectDate = inflate.findViewById(R.id.edt_eSelect_date);
        money = inflate.findViewById(R.id.edt_eMoney);
        description = inflate.findViewById(R.id.edt_eDescription);
        btnSave = inflate.findViewById(R.id.btn_eSave);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(selectDate);
            }
        });

//        Record r = new Record();
//        UserDData.get().getData().add(r);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        selectDate.setText(dtf.format(now).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflate.getContext(), android.R.layout.simple_spinner_dropdown_item, Category.expenseNames);
        dropdown.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long dtStart = Date.parse(selectDate.getText().toString());
                Date date = new Date(dtStart);
                Record r = new Record(selectDate.getText().toString(), Record.EXPENSE, Double.parseDouble(money.getText().toString()), dropdown.getSelectedItem().toString(), description.getText().toString(), 1);
                Log.i("Infor", r.getDate() + " " + r.getCategory() + " " + r.getMoney() + " " + r.getDescription());
                UserDData.get().getData().add(r);
                getActivity().finish();

            }
        });

        return inflate;
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

                new TimePickerDialog(ExpenseFragment.this.getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(ExpenseFragment.this.getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void initUI(View inflate) {
        dropdown = inflate.findViewById(R.id.spinner_eCategory);
        selectDate = inflate.findViewById(R.id.edt_eSelect_date);
        edtMoney = inflate.findViewById(R.id.edt_eMoney);
        edtDescription = inflate.findViewById(R.id.edt_eDescription);
        btnSave = inflate.findViewById(R.id.btn_eSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRecord();
            }
        });
    }

    private void addNewRecord() {
        double recordAmount = Double.parseDouble(edtMoney.getText().toString());
        Record newRecord = new Record(selectDate.getText().toString(), Record.EXPENSE, Double.parseDouble(edtMoney.getText().toString()), dropdown.getSelectedItem().toString(), edtDescription.getText().toString(), records.size() + 1);
        Map<String, Object> recordUpdate = newRecord.toMap();
        recordUpdate.put(String.valueOf(records.size() + 1), newRecord);
        Helper.updateObject("User/" + Helper.getUsername() + "/Records/", newRecord);
        recordAmount = amount - recordAmount;
        Helper.updateNumber("User/" + Helper.getUsername() + "/Amount", recordAmount);
    }
}