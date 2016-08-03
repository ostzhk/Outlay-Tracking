package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {
    final static int MENU_DELETE = 1;
    List<Outlay> outlayList;
    List<String> outlayListString = new ArrayList<>();
    DBHandler dbHandler;
    ListView lvMain;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_DELETE, 0, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        dbHandler.deleteThisOutlay(info.targetView.getId());
        showHistory();
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.history_activity);
        lvMain = (ListView) findViewById(R.id.listItem);
        dbHandler = new DBHandler(this);
        showHistory();

        registerForContextMenu(lvMain);


    }

    private void showHistory() {
        outlayList = dbHandler.getAllOutlays();

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,  outlayListString);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        for (final Outlay o : outlayList) {
            outlayListString.add(o.getDate() + " " + o.getCategory() + " "
                    + String.valueOf(o.getCount()) + " руб.");
        }

    }

    public void deleteAll(View view) {
        dbHandler.deleteOutlays();
        recreate();
    }
}
