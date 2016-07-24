package com.example.zhuki.outlaytracking;

/**
 * Created by Zhuki on 22.07.2016.
 */
public class Outlay {
    int id;
    int count;
    String category;
    public Outlay(String category, int count) {
        this.category=category;
        this.count=count;
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
