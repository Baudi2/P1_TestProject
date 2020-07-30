package ru.startandroid.develop.p0351_testproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int THIS_DATABASE_VERSION_2 = 2;
    public static final String THIS_DATABASE_NAME = "database";
    public static final String THIS_TABLE = "thisTable";

    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_ID = "id";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_URL = "url";
    public static final String KEY_TEXT = "text";

    public DBHelper(Context context) {
        super(context, THIS_DATABASE_NAME, null, THIS_DATABASE_VERSION_2);
    }// DBHelper constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + THIS_TABLE + "("+ KEY_ID + " TEXT,"+ KEY_EMAIL + " TEXT," +
                KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME + " TEXT," + KEY_AVATAR + " TEXT," + KEY_COMPANY + " TEXT," +
                KEY_URL + " TEXT," + KEY_TEXT + " TEXT" +")");
    }// onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists " + THIS_TABLE);
        onCreate(db);
    }// onUpgrade
}// class