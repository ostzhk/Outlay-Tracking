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
    private static final String DATE = "date";
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
                + CATEGORY + " TEXT," + DATE + " DATE)";

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
        values.put(DATE, outlay.getDate());
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
                outlay.setDate(cursor.getString(3));
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

    public Map<String, Outlay> getSumOfCategory(String date, String date2) {
        Map<String, Outlay> outlayList = new HashMap<>();
        String selectQuery = "SELECT CATEGORY, SUM(COUNT), DATE FROM " + OUTLAY_INFO + " WHERE DATE BETWEEN '"+date+"' " +
                "AND "+"'"+date2+"' GROUP BY CATEGORY ORDER BY COUNT DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay(cursor.getString(0), Integer.parseInt(cursor.getString(1)));
                outlay.setDate(cursor.getString(2));
                outlayList.put(cursor.getString(0), outlay);
            } while (cursor.moveToNext());
        }
        return outlayList;
    }

    public void deleteThisOutlay(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + OUTLAY_INFO + " WHERE " + ID + " = " + id;
        db.execSQL(query);
    }

    public void deleteThisCategory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + CATEGORY_TABLE + " WHERE " + ID + " = " + id;
        db.execSQL(query);
    }

    public void deleteOutlays(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OUTLAY_INFO, null, null);
    }

    public Outlay selectOutlay(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, DATE, CATEGORY, COUNT FROM " + OUTLAY_INFO + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        Outlay outlay = null;
        if (cursor.moveToFirst()) {
            do {
                 outlay = new Outlay(cursor.getString(2), Integer.parseInt(cursor.getString(3)));
                outlay.setId(cursor.getInt(0));
                outlay.setDate(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return outlay;
    }

    public void changeOutlay(Outlay outlay){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + OUTLAY_INFO + " SET DATE='" + outlay.getDate() + "', CATEGORY='" +
                outlay.getCategory() + "', COUNT=" + outlay.getCount() + " WHERE " + ID + " = " + outlay.getId();
        db.execSQL(query);
    }
}
