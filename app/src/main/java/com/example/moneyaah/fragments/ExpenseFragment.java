package com.example.moneyaah.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moneyaah.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class ExpenseFragment extends Fragment {

    String[] items = new String[]{"Ăn uống", "Giải trí", "Tự thân phát triển", "Giao thông vận tải", "Sở thích", "Sinh hoạt",
            "Áo quần", "Làm đẹp", "Sức khỏe", "Giáo dục", "Sự kiện", "Khác"};

    private EditText selectDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_income, container, false);
        Spinner dropdown = inflate.findViewById(R.id.spinner1);
        selectDate = inflate.findViewById(R.id.edt_select_date);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflate.getContext(),  android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        return inflate;
    }
}