package com.ddona.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.ddona.hello.provider.EngineerProvider/engineer");
        ContentValues values = new ContentValues();
        values.put("name","Doan");
        values.put("gen","3559123");
        values.put("single_id","doanpt");
        getContentResolver().insert(uri, values);
    }
}
