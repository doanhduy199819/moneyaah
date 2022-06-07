package com.example.moneyaah.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.moneyaah.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StatisticScreen extends AppCompatActivity {

    BarChart barChart;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_screen);

        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for(int i=1; i<6; i++){
            float value = (float) (i * 10.0);

            BarEntry barEntry = new BarEntry(i, value);

            PieEntry pieEntry = new PieEntry(i, "value" + i);

            barEntries.add(barEntry);
            pieEntries.add(pieEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        barDataSet.setDrawValues(true);

        barChart.setData(new BarData(barDataSet));

        barChart.animateY(2500);
        barChart.getDescription().setText("");
        barChart.getDescription().setTextColor(Color.BLUE);


        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieDataSet.setDrawValues(false);

        pieChart.setData(new PieData(pieDataSet));

        pieChart.animateXY(2500, 2500);
        pieChart.getDescription().setText("Detailed spending statistics");
        pieChart.getDescription().setTextColor(Color.BLUE);

    }
}