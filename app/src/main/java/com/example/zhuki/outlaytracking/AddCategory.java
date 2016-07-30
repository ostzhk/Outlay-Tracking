package com.example.zhuki.outlaytracking;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AddCategory extends Activity {
    EditText editText;
    DBHandler dbHandler;
    List<Category> categories;
    LinearLayout linearLayout;

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
        linearLayout = (LinearLayout) findViewById(R.id.addLayout);
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
            textView.setTextSize(26);
            textView.setText(c.getCategory());
            Button button = new Button(this);
            button.setText("Удалить");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteThisCategory(c.getId());
                    linearLayout.removeAllViewsInLayout();
                    showCategories();
                }
            });
            linearLayout.addView(textView);
            linearLayout.addView(button);
        }
    }

}
