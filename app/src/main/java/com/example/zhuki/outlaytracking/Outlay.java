package com.example.zhuki.outlaytracking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zhuki on 22.07.2016.
 */
public class Outlay {
    int id;
    int count;
    String category;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {

        return date;
    }

    String date;
    public Outlay(String category, int count) {
        this.category=category;
        this.count=count;
        this.date = getDateTime();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd.MM.yy HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public int getCount() {
        return count;
    }

    public String getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
