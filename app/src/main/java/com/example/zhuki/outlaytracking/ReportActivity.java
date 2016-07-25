package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportActivity extends Activity {
    Map<String, Outlay> outlayList;
    LinearLayout linearLayout;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.activity_report);
        linearLayout = (LinearLayout) findViewById(R.id.reportLayout);
        dbHandler=new DBHandler(this);
        outlayList=dbHandler.getSumOfCategory();
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
