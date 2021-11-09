package com.example.yackconsumer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE searchdata (md_name VARCHAR(100), md_code VARCHAR(100) PRIMARY KEY, md_stdday VARCHAR(100), md_price VARCHAR(100), save_time date, count_day VARCHAR(100), count_time VARCHAR(100), count_unit VARCHAR(100), md_check INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE searchdata");
        onCreate(sqLiteDatabase);
    }
}
