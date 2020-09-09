package com.ddona.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.ddona.hello.provider.EngineerProvider/engineer");
        ContentValues values = new ContentValues();
        values.put("name", "Doan");
        values.put("gen", "3559123");
        values.put("single_id", "doanpt");
        getContentResolver().insert(uri, values);


        Cursor cursor = getContentResolver().query(uri, null, null,
                null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String gen = cursor.getString(2);
                String singleId = cursor.getString(3);
                Log.d("doanpt", "query:" + id + " - "
                        + name + " - " + gen + " - " + singleId);
            }
        }
    }
}
