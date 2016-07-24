package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCategory extends Activity {
    EditText editText;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        editText = (EditText) findViewById(R.id.editText2);
        dbHandler = new DBHandler(this);
    }

    public void addCategory(View v){
        if (TextUtils.isEmpty(editText.getText().toString())){
            Toast toast = Toast.makeText(this, "Введите имя категории", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Category category = new Category(editText.getText().toString());
            dbHandler.addCategory(category);
            finish();
        }
    }
}
