package com.ddona.hello.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EngineerSQLiteHelper extends SQLiteOpenHelper{
    private static final String TAG = "SQLiteOpenHelper";

    public static final String DATABASE_NAME = "engineer";
    public static final int DATABASE_VERSION = 136;

    public static final String ENGINEER_TABLE = "engineer_table";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String GEN = "gen";
    public static final String SINGLE_ID = "single_id";

    public EngineerSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = "create table " + ENGINEER_TABLE
        		+ " (" + ID + " integer primary key autoincrement, "
        		+ NAME + " text, " + GEN + " integer, " + SINGLE_ID + " text);" ;
        Log.d(TAG, "onCreate " + command);
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String command = "drop table if exists " + ENGINEER_TABLE;
        db.execSQL(command);
        onCreate(db);
    }
}
