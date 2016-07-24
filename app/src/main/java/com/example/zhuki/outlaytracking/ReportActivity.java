package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends Activity {
    List<Outlay> outlayList;
    LinearLayout linearLayout;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.report_activity);
        linearLayout = (LinearLayout) findViewById(R.id.reportLayout);
        dbHandler=new DBHandler(this);
        outlayList=dbHandler.getAllOutlays();
        for (Outlay o:outlayList){
            TextView textView = new TextView(this);
            textView.setTextSize(26);
            textView.setText(o.getCategory() + ": -" + o.getCount() + " руб.");
            textView.setTextColor(Color.RED);
            linearLayout.addView(textView);
        }
    }
}
