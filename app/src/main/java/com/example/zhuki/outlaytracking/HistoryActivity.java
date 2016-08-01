package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class HistoryActivity extends Activity {
    List<Outlay> outlayList;
    TableLayout tableLayout;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.history_activity);
        tableLayout = (TableLayout) findViewById(R.id.historyLayout);
        dbHandler = new DBHandler(this);
        showHistory();

    }

    private void showHistory() {
        outlayList = dbHandler.getAllOutlays();

        for (final Outlay o : outlayList) {
            TextView textView = new TextView(this);
            textView.setTextSize(16);
            textView.setText(o.getDate() + " " + o.getCategory() + ": -" + o.getCount() + " руб.");
            textView.setGravity(Gravity.LEFT);
            textView.setWidth(450);


            Button button = new Button(this);
            button.setText("Удалить");
            button.setGravity(Gravity.RIGHT);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteThisOutlay(o.getId());
                    tableLayout.removeAllViewsInLayout();
                    showHistory();
                }
            });
            TableRow tableRow = new TableRow(this);
            tableRow.addView(textView);
            tableRow.addView(button);
            tableLayout.addView(tableRow);
        }
    }

    public void deleteAll(View view) {
        dbHandler = new DBHandler(this);
        dbHandler.deleteOutlays();
        recreate();
    }
}
