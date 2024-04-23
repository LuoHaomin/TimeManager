package com.example.timemanager.database;

import com.example.timemanager.bean.Plan;
import com.example.timemanager.bean.Schedule;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;

@SuppressLint("DefaultLocale")
public class DB_Schedule extends SQLiteOpenHelper {
    private static final String TAG = "DB_Schedule";
    private static final String DB_NAME = "Schedule.db"; // 数据库的名称
    public static final int DB_VERSION = 1; // 数据库的版本号
    private static DB_Schedule mHelper = null; // 数据库帮助器的实例
    private SQLiteDatabase mDB = null; // 数据库的实例
    public static final String TABLE_NAME = "Schedules"; // 表的名称

    private DB_Schedule(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private DB_Schedule(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static DB_Schedule getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new DB_Schedule(context, version);
        } else if (mHelper == null) {
            mHelper = new DB_Schedule(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    // 创建数据库，执行建表语句
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql:" + drop_sql);
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL ,"
                + "content TEXT NOT NULL ,"
                + "start_time TEXT ,"
                + "end_time TEXT ,"
                + "finish TEXT ,"
                + "position TEXT ,"
                + "repeat_mode TEXT ,"
                + "repeat_time TEXT ,"
                + "stuff TEXT ,"
                + "root TEXT"
                + ");";
        Log.d(TAG, "create_sql:" + create_sql);
        db.execSQL(create_sql); // 执行完整的SQL语句
    }

    // 升级数据库，执行表结构变更语句
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 根据指定条件删除表记录
    public int delete(String condition) {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, condition, null);
    }

    // 删除该表的所有记录
    public int deleteAll() {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, "1=1", null);
    }

    // 往该表添加一条记录
    public long insert(Schedule info) {
        List<Schedule> infoList = new ArrayList<>();
        infoList.add(info);
        return insert(infoList);
    }

    // 往该表添加多条记录
    public long insert(List<Schedule> infoList) {
        long result = -1;
        for (int i = 0; i < infoList.size(); i++) {
            Schedule info = infoList.get(i);
            List<Schedule> tempList = new ArrayList<Schedule>();


            // 不存在唯一性重复的记录，则插入新记录
            ContentValues cv = new ContentValues();
            cv.put("content",info.content);
            cv.put("finish",info.finish);
            cv.put("stuff",info.stuff);
            cv.put("repeat_mode",info.repeat_mode);
            cv.put("repeat_time",info.repeat_time);
            cv.put("position",info.position);
            cv.put("start_time", info.start_time);
            cv.put("end_time",info.end_time);
            cv.put("root",info.root);

            // 执行插入记录动作，该语句返回插入记录的行号
            result = mDB.insert(TABLE_NAME, "", cv);
            if (result == -1) { // 添加成功则返回行号，添加失败则返回-1
                return result;
            }
        }
        return result;
    }

    // 根据条件更新指定的表记录
    public int update(Schedule info, String condition) {
        ContentValues cv = new ContentValues();

        cv.put("content",info.content);
        cv.put("finish",info.finish);
        cv.put("stuff",info.stuff);
        cv.put("repeat_mode",info.repeat_mode);
        cv.put("repeat_time",info.repeat_time);
        cv.put("position",info.position);
        cv.put("start_time", info.start_time);
        cv.put("end_time",info.end_time);
        cv.put("root",info.root);
        // 执行更新记录动作，该语句返回更新的记录数量
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(Schedule info) {
        // 执行更新记录动作，该语句返回更新的记录数量
        return update(info, "_id = " + info.id);
    }


    // 根据指定条件查询记录，并返回结果数据列表
    public List<Schedule> query(String condition) {
        String sql = String.format("select * " +
                "from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: " + sql);
        List<Schedule> infoList = new ArrayList<Schedule>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            Schedule info = new Schedule();
            info.id = cursor.getLong(0);
            info.content = cursor.getString(1);
            info.start_time= cursor.getString(2);
            info.end_time = cursor.getString(3);
            info.finish = cursor.getString(4);
            info.position = cursor.getString(5);
            info.repeat_mode=cursor.getString(6);
            info.repeat_time=cursor.getString(7);
            info.stuff = cursor.getString(8);
            info.root = cursor.getString(9);
            infoList.add(info);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return infoList;
    }
    public List<Schedule> query(){
        String sql = String.format("select * " +
                "from %s ;", TABLE_NAME);
        List<Schedule> infoList = new ArrayList<Schedule>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            Schedule info = new Schedule();
            info.id = cursor.getLong(0);
            info.content = cursor.getString(1);
            info.start_time= cursor.getString(2);
            info.end_time = cursor.getString(3);
            info.finish = cursor.getString(4);
            info.position = cursor.getString(5);
            info.repeat_mode=cursor.getString(6);
            info.repeat_time=cursor.getString(7);
            info.stuff = cursor.getString(8);
            info.root = cursor.getString(9);
            infoList.add(info);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return infoList;
    }
}
