package com.example.timemanager.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.timemanager.adapter.EvaluationAdapter;
import com.example.timemanager.bean.Evaluation;
import com.example.timemanager.bean.Schedule;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@SuppressLint("DefaultLocale")
public class DB_Evaluation extends SQLiteOpenHelper {
    private static final String TAG = "DB_Evaluation";
    private static final String DB_NAME = "Evaluation.db";
    public static final int DB_VERSION = 1;
    private static DB_Evaluation mHelper = null;// 数据库帮助器的实例
    private SQLiteDatabase mDB = null;
    public static final String TABLE_NAME = "Evaluations";

    private DB_Evaluation(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    private DB_Evaluation(Context context, int version){
        super(context, DB_NAME, null, version);
    }

    public static DB_Evaluation getInstance(Context context, int version){
        if (version > 0 && mHelper == null){
            mHelper = new DB_Evaluation(context, version);
        } else if (mHelper == null) {
            mHelper = new DB_Evaluation(context);
        }
        return mHelper;
    }


    public SQLiteDatabase openReadLink(){
        if (mDB == null || !mDB.isOpen()){
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }
    public SQLiteDatabase openWriteLink(){
        if (mDB == null || !mDB.isOpen()){
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void closeLink(){
        if (mDB != null && mDB.isOpen()){
            mDB.close();
            mDB = null;
        }
    }


//    todo:questions--execSQL used as debug returner and data inserter?
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS" + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql:" + drop_sql);
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME +"("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "content TEXT NOT NULL, "
                + "time TEXT"
                + ");";
        Log.d(TAG, "create_sql:" + create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {}

    public int delete(String condition){
        return mDB.delete(TABLE_NAME, condition, null);
    }

    public int deleteAll(){
        return mDB.delete(TABLE_NAME, "1=1", null);
    }

    public long insert(Evaluation info){
        List<Evaluation> infoList = new ArrayList<Evaluation>();
        infoList.add(info);
        return insert(infoList);
    }

    public long insert(List<Evaluation> infoList){
        long result = -1;
        for (int i = 0; i < infoList.size(); i++){
            Evaluation info = infoList.get(i);
            List<Evaluation> tempList = new ArrayList<Evaluation>();

            ContentValues cv = new ContentValues();
            cv.put("content", info.content);
            cv.put("time", info.time);

            result = mDB.insert(TABLE_NAME, "", cv);
            if (result == -1){
                return result;
            }
        }
        return result;
    }

//    update according to given conditions
    public int update(Evaluation info, String condition){
        ContentValues cv = new ContentValues();

        cv.put("content", info.content);
        cv.put("time", info.time);

        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(Evaluation info){
        return update(info, "_id = " + info.id);
    }

    public List<Evaluation> query(String condition){
        String sql = String.format("select * " +
                "from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql" + sql);
        List<Evaluation> infoList = new ArrayList<Evaluation>();
        Cursor cursor = mDB.rawQuery(sql, null);
        while(cursor.moveToNext()){
            Evaluation info = new Evaluation();
            info.id = cursor.getLong(0);
            info.content = cursor.getString(1);
            info.time = cursor.getString(2);
            infoList.add(info);
        }
        cursor.close();
        return infoList;
    }
}
