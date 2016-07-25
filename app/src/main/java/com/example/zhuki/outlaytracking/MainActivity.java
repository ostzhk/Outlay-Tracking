package com.example.zhuki.outlaytracking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner dropdown;
    EditText editText;
    DBHandler dbHandler;
    ArrayList<String> categoryList = new ArrayList<>();
    List<Category> categories;
    String selected_item;
    private int year, month, day;
    Calendar calendar;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        dropdown = (Spinner) findViewById(R.id.spinner);
        dbHandler = new DBHandler(this);
        showCategories();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selected_item = dropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        if (editText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCategories();
    }

    public void addCategory(View view) {
        Intent intent = new Intent(this, AddCategory.class);
        startActivity(intent);
    }

    public void addOutlay(View view) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Toast toast = Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Outlay outlay = new Outlay(selected_item, Integer.parseInt(editText.getText().toString()));
            outlay.setDate(date);
            dbHandler.addOutlay(outlay);
            Toast toast = Toast.makeText(this, "- " + editText.getText().toString() + " руб.", Toast.LENGTH_SHORT);
            toast.show();
            editText.setText("");
        }
    }

    public void reportActivity(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public void historyActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void showCategories() {
        categories = dbHandler.getAllCategories();
        if (!categories.isEmpty()) {
            categoryList.clear();
            for (Category c : categories) {
                categoryList.add(c.getCategory());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        dropdown.setAdapter(adapter);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        Button button = (Button) findViewById(R.id.buttonDate);
        date = new StringBuilder().append(day).append("/").append(month).append("/").append(year).toString();
        button.setText(date);
    }

    public void pickDate(View view) {
        showDialog(999);
    }
}
