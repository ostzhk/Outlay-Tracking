package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends Activity {
    List<Outlay> outlayList;
    LinearLayout linearLayout;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.history_activity);
        linearLayout = (LinearLayout) findViewById(R.id.historyLayout);
        dbHandler=new DBHandler(this);
        outlayList=dbHandler.getAllOutlays();
        for (Outlay o:outlayList){
            TextView textView = new TextView(this);
            textView.setTextSize(26);
            textView.setText(o.getDate() + " " + o.getCategory() + ": -" + o.getCount() + " руб.");
            linearLayout.addView(textView);
        }
    }

    public void deleteAll(View view){
        dbHandler=new DBHandler(this);
        dbHandler.deleteOutlays();
        recreate();
    }
}
