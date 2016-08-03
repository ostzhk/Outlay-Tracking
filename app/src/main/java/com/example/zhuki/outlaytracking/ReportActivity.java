package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ReportActivity extends Activity {
    Map<String, Outlay> outlayList;
    PieChart pieChart;
    DBHandler dbHandler;
    Calendar calendar;
    String date;
    String date2;
    int anInt;
    int finalSum;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2, arg3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.activity_report);
        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);


        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day);
        dbHandler = new DBHandler(this);
        outlayList = dbHandler.getSumOfCategory(date, date2);

        finalSum = 0;
        outlayList=dbHandler.getSumOfCategory(date, date2);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        setData();

        pieChart.setEntryLabelColor(Color.DKGRAY);
        pieChart.setEntryLabelTextSize(18f);
        pieChart.animateXY(2000, 2000);


    }

    private void setData() {


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();


        for (Map.Entry<String, Outlay> entry : outlayList.entrySet()) {
            entries.add(new PieEntry((float) entry.getValue().getCount(),
                    entry.getKey() + " - " + entry.getValue().getCount() + " руб."));
            finalSum += entry.getValue().getCount();
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setDrawValues(false);
        pieChart.setDrawSliceText(false);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setData(data);
        pieChart.setCenterText("Итого: - " + finalSum + " руб.");
        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private void showDate(int year, int month, int day) {
        Button button = (Button) findViewById(R.id.button6);
        date = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).toString();
        button.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).toString());

        Button button1 = (Button) findViewById(R.id.button7);
        date2 = new StringBuilder().append(year).append("-").append(month + 2).append("-").append(day).toString();
        button1.setText(new StringBuilder().append(day).append("-").append(month + 2).append("-").append(year).toString());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        } else if (id == 998) {
            return new DatePickerDialog(this, myDateListener, year, month + 1, day);
        }
        return null;
    }

    public void pickDate(View view) {
        anInt = view.getId();
        if (anInt == R.id.button6) {
            showDialog(999);
        } else if (anInt == R.id.button7) {
            showDialog(998);
        }
    }

    public void showReport(View view){
        finalSum = 0;
        outlayList=dbHandler.getSumOfCategory(date, date2);
        setData();
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();
    }
}
