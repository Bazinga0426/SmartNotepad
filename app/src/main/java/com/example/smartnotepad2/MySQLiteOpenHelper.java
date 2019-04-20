package com.example.smartnotepad2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanghonggang on 2018/6/29.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME ="smart_notepad.db";
    private final static int VERSION = 1;//版本号
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
    }
    public MySQLiteOpenHelper(Context cxt){
        this(cxt, DB_NAME, null, VERSION);
    }

    public MySQLiteOpenHelper(Context cxt,int version) {
        this(cxt,DB_NAME,null,version);
    }
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+ GlobalParams.DB_NAME+" ( " +GlobalParams.DB_VALUE_ID_KEY+
                " integer primary key autoincrement, " +GlobalParams.DB_VALUE_DATE_KEY+" integer , "+
                GlobalParams.DB_VALUE_TIME_KEY+" varchar(20) , "+
                GlobalParams.DB_VALUE_CONTENT_KEY+" text )";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
