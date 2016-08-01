package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AddCategory extends Activity {
    EditText editText;
    DBHandler dbHandler;
    List<Category> categories;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NewTheme);
        setContentView(R.layout.activity_add_category);
        editText = (EditText) findViewById(R.id.editText2);
        dbHandler = new DBHandler(this);
        if(editText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        tableLayout = (TableLayout) findViewById(R.id.addLayout);
        showCategories();
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

    private void showCategories(){
        categories = dbHandler.getAllCategories();
        for (final Category c:categories) {
            TextView textView = new TextView(this);
            textView.setTextSize(16);
            textView.setText(c.getCategory());
            textView.setGravity(Gravity.LEFT);
            textView.setWidth(450);

            Button button = new Button(this);
            button.setText("Удалить");
            button.setGravity(Gravity.RIGHT);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteThisCategory(c.getId());
                    tableLayout.removeAllViewsInLayout();
                    showCategories();
                }
            });
            TableRow tableRow = new TableRow(this);
            tableRow.addView(textView);
            tableRow.addView(button);
            tableLayout.addView(tableRow);
        }
    }

}
