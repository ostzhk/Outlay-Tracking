package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        dbHandler = new DBHandler(this);
        showHistory();
    }

    private void showHistory() {
        outlayList = dbHandler.getAllOutlays();
        for (final Outlay o : outlayList) {
            TextView textView = new TextView(this);
            textView.setTextSize(26);
            textView.setText(o.getDate() + " " + o.getCategory() + ": -" + o.getCount() + " руб.");
            Button button = new Button(this);
            button.setText("Удалить");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteThisOutlay(o.getId());
                    linearLayout.removeAllViewsInLayout();
                    showHistory();
                }
            });
            linearLayout.addView(textView);
            linearLayout.addView(button);
        }
    }

    public void deleteAll(View view) {
        dbHandler = new DBHandler(this);
        dbHandler.deleteOutlays();
        recreate();
    }
}
