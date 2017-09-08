package com.bwie.riko8_30.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/9/5.
 */

public class MySQliteHelper extends SQLiteOpenHelper {

    public MySQliteHelper(Context context) {
        super(context, "xiazai", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table toutiao(type varchar(10),json text)");
        db.execSQL("create table channel(json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
