package com.ddona.hello.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ddona.hello.db.EngineerSQLiteHelper;

public class EngineerProvider extends ContentProvider {
    private static final String AUTHORITY = "com.ddona.hello.provider.EngineerProvider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/engineer");
    private static final UriMatcher URI_MATCHER;

    private static final int URI_TABLE = 1001;

    private static final int URI_ID = 1002;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, "engineer", URI_TABLE);
        URI_MATCHER.addURI(AUTHORITY, "engineer/#", URI_ID);
    }

    private EngineerSQLiteHelper sqLiteHelper;

    @Override
    public boolean onCreate() {
        sqLiteHelper = new EngineerSQLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (URI_MATCHER.match(uri) != URI_TABLE) {
            throw new IllegalArgumentException("I don't know you... please try with another!");
        }

        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        long rowId = db.insert(EngineerSQLiteHelper.ENGINEER_TABLE, null, contentValues);
        if (rowId > 0) {
            Uri result = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(result, null);
            return result;
        }
        throw new IllegalArgumentException("Fail to insert with uri:" + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
