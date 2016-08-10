package com.example.zhuki.outlaytracking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChangeOutlay extends AppCompatActivity {
    DBHandler dbHandler;
    LinearLayout linearLayout;
    private int year, month, day;
    Calendar calendar;
    String date;
    Button button;
    ArrayList<String> categoryList = new ArrayList<>();
    List<Category> categories;
    Spinner dropdown;
    EditText text;
    int id, count;
    String category, dateOutlay;
    String selected_item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_outlay_activity);
        dbHandler = new DBHandler(this);
        Button btnOk = (Button)findViewById(R.id.button10);
        Button btnCancel = (Button)findViewById(R.id.button11);
        text = (EditText)findViewById(R.id.editText3);
        id = getIntent().getExtras().getInt("id");
        count = getIntent().getExtras().getInt("Count");
        dateOutlay = getIntent().getExtras().getString("Date");
        category = getIntent().getExtras().getString("Category");

        linearLayout = (LinearLayout) findViewById(R.id.change_outlay);
        dropdown = (Spinner)findViewById(R.id.spinner2);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        button = (Button)findViewById(R.id.button9);
        showDate(year, month, day);
        showCategories();
        text.setText(String.valueOf(count));
        text.setWidth(100);
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
        if (text.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
            showDate(arg1, arg2, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        date = new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).toString();
        button.setText(new StringBuilder().append(day).append("-").append(month+1).append("-").append(year).toString());
    }

    public void pickDate(View view) {
        showDialog(999);
    }

    private void showCategories() {
        categoryList.clear();
        categories = dbHandler.getAllCategories();
        if (!categories.isEmpty()) {
            for (Category c : categories) {
                categoryList.add(c.getCategory());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        dropdown.setAdapter(adapter);
//        dropdown.setSelection();
    }

    public void change(View view){
        Outlay outlay = new Outlay(dropdown.getSelectedItem().toString(), Integer.parseInt(text.getText().toString()));
        outlay.setId(id);
        outlay.setDate(date);
        dbHandler.changeOutlay(outlay);
        finish();
    }

    public void cancel(View view){
        finish();
    }
}
