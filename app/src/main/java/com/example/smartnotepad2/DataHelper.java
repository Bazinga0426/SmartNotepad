package com.example.smartnotepad2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghonggang on 2018/6/29.
 */

public class DataHelper {
    MySQLiteOpenHelper helper = null;

    public DataHelper(Context cxt) {
        helper = new MySQLiteOpenHelper(cxt);
    }

    public DataHelper(Context cxt, int version) {
        helper = new MySQLiteOpenHelper(cxt, version);
    }

    public void insertData(NotepadBean notepadBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GlobalParams.DB_VALUE_DATE_KEY, notepadBean.getDate() + "");
        cv.put(GlobalParams.DB_VALUE_TIME_KEY, notepadBean.getTime());
        cv.put(GlobalParams.DB_VALUE_CONTENT_KEY, notepadBean.getContent());
        db.insert(GlobalParams.DB_NAME, null, cv);
        cv.clear();
        db.close();
        helper.close();
    }

    public List<NotepadBean> getNotepadList() {
        List<NotepadBean> notepadBeanList = new ArrayList<NotepadBean>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + GlobalParams.DB_NAME + " order by " + GlobalParams.DB_VALUE_DATE_KEY, null);
        while (c.moveToNext()) {
            NotepadBean notepadBean = new NotepadBean();
            notepadBean.setId(c.getInt(c.getColumnIndex(GlobalParams.DB_VALUE_ID_KEY)));

            notepadBean.setDate(c.getInt(c.getColumnIndex(GlobalParams.DB_VALUE_DATE_KEY)));
            notepadBean.setTime(c.getString(c.getColumnIndex(GlobalParams.DB_VALUE_TIME_KEY)));
            notepadBean.setContent(c.getString(c.getColumnIndex(GlobalParams.DB_VALUE_CONTENT_KEY)));
            notepadBeanList.add(notepadBean);
        }
        c.close();
        db.close();
        helper.close();
        return notepadBeanList;
    }

    public void deleteNotepad(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(GlobalParams.DB_NAME, GlobalParams.DB_VALUE_ID_KEY + " = ?", new String[]{id + ""});
        db.close();
        helper.close();
    }

    public void update(int id, String date, String time, String content) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GlobalParams.DB_VALUE_DATE_KEY, date);
        cv.put(GlobalParams.DB_VALUE_TIME_KEY, time);
        cv.put(GlobalParams.DB_VALUE_CONTENT_KEY, content);
        db.update(GlobalParams.DB_NAME, cv, GlobalParams.DB_VALUE_ID_KEY + " = ?", new String[]{id + ""});
        cv.clear();
        db.close();
        helper.close();
    }
}
