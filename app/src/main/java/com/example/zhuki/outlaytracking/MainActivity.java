package com.example.zhuki.outlaytracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner dropdown;
    EditText editText;
    DBHandler dbHandler;
    ArrayList<String> categoryList = new ArrayList<>();
    List<Category> categories;
    String selected_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        dropdown = (Spinner) findViewById(R.id.spinner);
        dbHandler = new DBHandler(this);
        showCategories();
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
            dbHandler.addOutlay(new Outlay(selected_item, Integer.parseInt(editText.getText().toString())));
            Toast toast = Toast.makeText(this, "- " + editText.getText().toString() + " руб.", Toast.LENGTH_SHORT);
            toast.show();
            editText.setText("");
        }
    }

    public void reportActivity(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
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
}
