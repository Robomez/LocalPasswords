package com.example.passwords2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* Subclass of SQLiteOpenHelper */
public class DataBaseHelper extends SQLiteOpenHelper{

    /* Table name */
    public static final String TABLE_NAME = "pass";

    /* Table columns */
    public static final String _ID = "_id";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String FILTER = "filter";

    /* Info about database */
    static final String DB_NAME = "passDB";

    /* Database version */
    static final int DB_VER = 1;

    /* Creating table query*/
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACCOUNT + " TEXT, "
            + PASSWORD + " TEXT, " + FILTER + " TEXT);";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
