package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity implements View.OnClickListener {
    List<Outlay> outlayList;
    DBHandler dbHandler;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.history_activity);
        dbHandler = new DBHandler(this);
        tableLayout = (TableLayout) findViewById(R.id.historyLayout);
        showHistory();
    }

    private void showHistory() {
        outlayList = dbHandler.getAllOutlays();
        for (Outlay o : outlayList) {
            TableRow tableRow = new TableRow(this);
            TextView textView= new TextView(this);
            Button button = new Button(this);

            textView.setText(o.getDate() + o.getCategory() + o.getCount());

            button.setBackgroundResource(R.drawable.ic_mode_edit_black_18dp);
            button.setOnClickListener(this);
            button.setGravity(Gravity.END);



            tableRow.addView(textView);
            tableRow.addView(button);
            tableLayout.addView(tableRow);
        }

    }

    public void deleteAll(View view) {
        dbHandler.deleteOutlays();
        recreate();
    }

    @Override
    public void onClick(View v) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(this, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.history_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });

        popup.show(); //showing popup menu
    }
}
