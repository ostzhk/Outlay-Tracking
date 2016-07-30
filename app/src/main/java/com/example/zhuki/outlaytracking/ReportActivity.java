package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportActivity extends Activity {
    Map<String, Outlay> outlayList;
    LinearLayout linearLayout;
    DBHandler dbHandler;
    private int year, month, day;
    Calendar calendar;
    String date;
    String date2;
    int anInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.activity_report);
        linearLayout = (LinearLayout) findViewById(R.id.reportLayout);
        dbHandler=new DBHandler(this);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day);

    }

    private void showDate(int year, int month, int day) {
        Button button = (Button) findViewById(R.id.button6);
        date = new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).toString();
        button.setText(new StringBuilder().append(day).append("-").append(month+1).append("-").append(year).toString());

        Button button1 = (Button) findViewById(R.id.button7);
        date2 = new StringBuilder().append(year).append("-").append(month+2).append("-").append(day).toString();
        button1.setText(new StringBuilder().append(day).append("-").append(month+2).append("-").append(year).toString());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }else if (id==998){
            return new DatePickerDialog(this, myDateListener, year, month+1, day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2, arg3);
        }
    };

    public void pickDate(View view) {
        anInt = view.getId();
        if (anInt==R.id.button6){
            showDialog(999);
        }else if (anInt==R.id.button7){
            showDialog(998);
        }
    }

    public void showReport(View view){
        linearLayout.removeAllViews();
        outlayList=dbHandler.getSumOfCategory(date, date2);
        for(Map.Entry<String, Outlay> entry : outlayList.entrySet()) {
            String key = entry.getKey();
            Outlay value = entry.getValue();
            TextView textView = new TextView(this);
            textView.setTextSize(26);
            textView.setText(key + ": -" + value.getCount() + " руб.");
            linearLayout.addView(textView);
        }
    }
}
