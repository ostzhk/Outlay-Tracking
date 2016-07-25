package com.example.zhuki.outlaytracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhuki on 22.07.2016.
 */
public class DBHandler extends SQLiteOpenHelper {
    //DB settings
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "dbo";

    //Tables cells
    private static final String OUTLAY_INFO = "outlay";
    private static final String ID = "id";
    private static final String COUNT = "count";
    private static final String CATEGORY = "category";
    private static final String CATEGORY_TABLE = "category_table";
    private static final String DELETED = "deleted";
    Context context;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OUTLAY_INFO = "CREATE TABLE " + OUTLAY_INFO + "("
                + ID + " INTEGER PRIMARY KEY," + COUNT + " INTEGER NOT NULL,"
                + CATEGORY + " TEXT" + ")";

        String CREATE_CATEGORY = "CREATE TABLE " + CATEGORY_TABLE + "("
                + ID + " INTEGER PRIMARY KEY," + CATEGORY + " TEXT NOT NULL," + DELETED + " BOOLEAN" + ")";
        db.execSQL(CREATE_OUTLAY_INFO);
        db.execSQL(CREATE_CATEGORY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void addOutlay(Outlay outlay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY, outlay.getCategory());
        values.put(COUNT, outlay.getCount());
        db.insert(OUTLAY_INFO, null, values);
        db.close();
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY, category.getCategory());
        values.put(DELETED, false);
        db.insert(CATEGORY_TABLE, null, values);
        db.close();
    }

    public List<Outlay> getAllOutlays() {
        List<Outlay> outlayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + OUTLAY_INFO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay(cursor.getString(2), Integer.parseInt(cursor.getString(1)));
                outlay.setId(Integer.parseInt(cursor.getString(0)));
                outlayList.add(outlay);
            } while (cursor.moveToNext());
        }
        return outlayList;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(cursor.getString(1));
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setDeleted(Boolean.parseBoolean(cursor.getString(2)));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        return categoryList;
    }

    public Map<String, Outlay> getSumOfCategory() {
        Map<String, Outlay> outlayList = new HashMap<>();
        String selectQuery = "SELECT CATEGORY, SUM(COUNT) FROM " + OUTLAY_INFO + " GROUP BY CATEGORY";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay(cursor.getString(0), Integer.parseInt(cursor.getString(1)));
                outlayList.put(cursor.getString(0), outlay);
            } while (cursor.moveToNext());
        }
        return outlayList;
    }

    public void deleteOutlays(){
        //String selectQuery = "SELECT CATEGORY, SUM(COUNT) FROM " + OUTLAY_INFO + " GROUP BY CATEGORY";
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OUTLAY_INFO, null, null);
    }
}
