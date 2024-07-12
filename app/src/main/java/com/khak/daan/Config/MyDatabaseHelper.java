package com.khak.daan.Config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String LIKEDTABLE = "Liked_table";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LIKEDTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,TITLETOP TEXT,CAT TEXT,TITLE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LIKEDTABLE);
    }

    public void insertLiked(String title_top,String cat,String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLETOP", title_top);
        contentValues.put("CAT", cat);
        contentValues.put("TITLE", title);
        System.out.println("insert::"+cat+title);
        long result = db.insert(LIKEDTABLE, null, contentValues);
        System.out.println("insert::"+result);
    }

    public Cursor getLikedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("cursor::2"+db);
        Cursor res = db.rawQuery("select * from " + LIKEDTABLE, null);
        System.out.println("cursor::3"+res);
        return res;
    }

    public Cursor getLikedOneData(String title_t,String cat, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("cursor::2"+db);
        String query = "SELECT * FROM " + LIKEDTABLE + " WHERE TITLETOP = ? + CAT = ? + TITLE = ?";
        String[] selectionArgs = {title_t, cat, title };
        Cursor res = db.rawQuery(query, selectionArgs);
        System.out.println("cursor::3"+res);
        return res;
    }

    public void deleteLikedData(String title_t,String cat, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("result::"+cat+title);
        long result = db.delete(LIKEDTABLE, "TITLETOP = ? AND CAT = ? AND TITLE = ?", new String[]{title_t, cat, title});
        System.out.println("result::"+result);
    }

}