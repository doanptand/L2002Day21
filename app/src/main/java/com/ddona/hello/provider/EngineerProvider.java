package com.ddona.hello.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

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
    public Cursor query(@NonNull Uri uri, @Nullable String[] projections,
                        @Nullable String selections, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(EngineerSQLiteHelper.ENGINEER_TABLE);
        if (URI_MATCHER.match(uri) == URI_ID) {
            String id = uri.getPathSegments().get(1);
            builder.appendWhere("id=" + id);
        }
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = builder.query(db, projections, selections, selectionArgs,
                null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
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
        Log.d("doanpt", "insert result is:" + rowId);
        if (rowId > 0) {
            Uri result = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(result, null);
            return result;
        }
        throw new IllegalArgumentException("Fail to insert with uri:" + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case URI_TABLE:
                count = db.delete(EngineerSQLiteHelper.ENGINEER_TABLE, selection, selectionArgs);
                break;
            case URI_ID:
                String id = uri.getPathSegments().get(1);
                String where = EngineerSQLiteHelper.ID + " = '" + id + "'";
                if (selection != null) {
                    where = where + " and " + selection;
                }
                count = db.delete(EngineerSQLiteHelper.ENGINEER_TABLE, where, selectionArgs);
                break;
            default:
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case URI_TABLE:
                count = db.update(EngineerSQLiteHelper.ENGINEER_TABLE, values, selection, selectionArgs);
                break;
            case URI_ID:
                String id = uri.getPathSegments().get(1);
                String where = EngineerSQLiteHelper.ID + " = '" + id + "'";
                if (selection != null) {
                    where = where + " and " + selection;
                }
                count = db.update(EngineerSQLiteHelper.ENGINEER_TABLE, values, where, selectionArgs);
                break;
            default:
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
