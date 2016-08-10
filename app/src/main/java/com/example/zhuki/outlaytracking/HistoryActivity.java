package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Size;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity implements View.OnClickListener {
    List<Outlay> outlayList;
    DBHandler dbHandler;
    TableLayout tableLayout;
    int id;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.history_activity);
        dbHandler = new DBHandler(this);
        tableLayout = (TableLayout) findViewById(R.id.historyLayout);
        intent = new Intent(this, ChangeOutlay.class);
        showHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showHistory();
    }

    private void showHistory() {
        tableLayout.removeAllViews();
        outlayList = dbHandler.getAllOutlays();
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 9;
        for (Outlay o : outlayList) {
            TableRow tableRow = new TableRow(this);
            TextView textView= new TextView(this);
            ImageView imageView = new ImageView(this);

            imageView.setOnClickListener(this);
            imageView.setBackgroundResource(R.drawable.ic_mode_edit_black_18dp);
            imageView.setId(o.getId());


            textView.setText(o.getDate() + o.getCategory() + o.getCount());
            textView.setTextSize(18);
            textView.setLayoutParams(params);


            tableRow.addView(textView);
            tableRow.addView(imageView);
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

        id = v.getId();

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.edit){
                    Outlay outlay = dbHandler.selectOutlay(id);
                    if (outlay!=null){
                        intent.putExtra("id", outlay.getId());
                        intent.putExtra("Date", outlay.getDate());
                        intent.putExtra("Category", outlay.getCategory());
                        intent.putExtra("Count", outlay.getCount());
                        startActivity(intent);
                    }
                }else if (item.getItemId()==R.id.delete){
                    showDialog(1);
                }
                return true;
            }
        });

        popup.show(); //showing popup menu
    }

    protected Dialog onCreateDialog(int id) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);

            // сообщение
            adb.setMessage("Вы действительно хотите удалить запись?");
            // кнопка положительного ответа
            adb.setPositiveButton("Да", myClickListener);
            // кнопка отрицательного ответа
            adb.setNegativeButton("Нет", myClickListener);
            return adb.create();
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    dbHandler.deleteThisOutlay(id);
                    showHistory();
                    break;
                // негаитвная кнопка
                case Dialog.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
            }
        }
    };
}
