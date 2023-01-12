/*
https://www.digitalocean.com/community/tutorials/android-sqlite-database-example-tutorial
Startandroid lessons explain SQLite support only in context of one Activity.
Anupam's tutorial helped understand how to isolate SQLite to another class and link it to several activities.
Also, using SQLite cursor to return a list is explained pretty well.
However, it misses one file and some changes were needed to suit code for my application.
*/

package com.example.passwords2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {

    private DataBaseHelper dataBaseHelper;

    private Context context;

    private SQLiteDatabase database;

    public DataBaseManager(Context c) {
        context = c;
    }

    public DataBaseManager open() throws SQLException {
        dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public void insert(String account, String password, String filter) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.ACCOUNT, account);
        contentValues.put(DataBaseHelper.PASSWORD, password);
        contentValues.put(DataBaseHelper.FILTER, filter);
        database.insert(DataBaseHelper.TABLE_NAME, null, contentValues);
    }

    public Cursor crawl() {
        String[] columns = new String[] {DataBaseHelper._ID, DataBaseHelper.ACCOUNT,
                DataBaseHelper.PASSWORD, DataBaseHelper.FILTER};
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, columns, null,null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String account, String password, String filter) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.ACCOUNT, account);
        contentValues.put(DataBaseHelper.PASSWORD, password);
        contentValues.put(DataBaseHelper.FILTER, filter);
        int i = database.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper._ID
                + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper._ID + "="
        + _id, null);
    }
}
