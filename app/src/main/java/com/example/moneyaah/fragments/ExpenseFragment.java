package com.example.moneyaah.fragments;

import static com.example.moneyaah.activity.MainActivity.amount;
import static com.example.moneyaah.activity.MainActivity.records;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moneyaah.Helper;
import com.example.moneyaah.R;
import com.example.moneyaah.Record;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;


public class ExpenseFragment extends Fragment {

    String[] items = new String[]{"Ăn uống", "Giải trí", "Tự thân phát triển", "Giao thông vận tải", "Sở thích", "Sinh hoạt",
            "Áo quần", "Làm đẹp", "Sức khỏe", "Giáo dục", "Sự kiện", "Khác"};

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
        View inflate = inflater.inflate(R.layout.fragment_expense_fragent, container, false);
        initUI(inflate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ExpenseFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
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

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        selectDate.setText(dtf.format(now).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflate.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        return inflate;
    }

    private void initUI(View inflate) {
        dropdown = inflate.findViewById(R.id.spinner1);
        selectDate = inflate.findViewById(R.id.edt_select_date);
        edtMoney = inflate.findViewById(R.id.edt_money1);
        edtDescription = inflate.findViewById(R.id.edt_desc1);
        btnSave = inflate.findViewById(R.id.btn_add_expense);
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
        Helper.updateObject("User/"+Helper.getUsername(getActivity())+"/Records/", newRecord);
        recordAmount = amount - recordAmount;
        Helper.updateNumber("User/"+Helper.getUsername(getActivity())+"/Amount", recordAmount);
    }
}