package com.example.moneyaah.fragments;

import static com.example.moneyaah.activity.MainActivity.amount;
import static com.example.moneyaah.activity.MainActivity.records;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.moneyaah.Helper;
import com.example.moneyaah.R;
import com.example.moneyaah.Record;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;

public class IncomeFragment extends Fragment {

    String[] items = new String[]{"Trả thêm giờ", "Tiền lương", "Tiền cấp", "Tiền thưởng", "Khác"};

    private EditText selectDate;
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
        initUi(inflate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        IncomeFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        String date = day + "/" + month + "/" + year;
                        selectDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRecord();
            }
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        selectDate.setText(dtf.format(now).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflate.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        return inflate;
    }


    private void initUi(View inflate) {
        selectDate = inflate.findViewById(R.id.edt_select_date);
        btnSave = inflate.findViewById(R.id.btn_save);
        edtMoney = inflate.findViewById(R.id.edt_money);
        edtDescription = inflate.findViewById(R.id.edt_desc);
        dropdown = inflate.findViewById(R.id.spinner1);
    }

    private void addNewRecord() {
        double recordAmount = Double.parseDouble(edtMoney.getText().toString());
        Record newRecord = new Record(selectDate.getText().toString(), Record.INCOME, recordAmount, dropdown.getSelectedItem().toString(), edtDescription.getText().toString(), records.size() + 1);
        Map<String, Object> recordUpdate = newRecord.toMap();
        recordUpdate.put(String.valueOf(records.size() + 1), newRecord);
        Helper.updateObject("User/" + Helper.getUsername(getActivity()) + "/Records/", newRecord);
        recordAmount = amount + recordAmount;
        Helper.updateNumber("User/" + Helper.getUsername(getActivity()) + "/Amount", recordAmount);
    }
}