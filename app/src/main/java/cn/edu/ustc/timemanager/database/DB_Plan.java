package cn.edu.ustc.timemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import cn.edu.ustc.timemanager.bean.Plan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_Plan extends SQLiteOpenHelper {
    private static final String TAG = "DB_Plan";
    private static final String DB_NAME = "Plan.db"; // 数据库的名称
    private static final int DB_VERSION = 1; // 数据库的版本号
    private static DB_Plan mHelper = null; // 数据库帮助器的实例
    private SQLiteDatabase mDB = null; // 数据库的实例
    public static final String TABLE_NAME = "Plans"; // 表的名称

    private DB_Plan(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private DB_Plan(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static DB_Plan getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new DB_Plan(context, version);
        } else if (mHelper == null) {
            mHelper = new DB_Plan(context);
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
                + "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                + "tag TEXT ,"
                + "start_time TEXT ,"
                + "end_time TEXT ,"
                + "content TEXT NOT NULL,"
                + "finish TEXT,"
                + "breakdown TEXT,"
                + "schedule TEXT"
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
    public long insert(Plan info) throws SQLException {
        List<Plan> infoList = new ArrayList<>();
        infoList.add(info);
        return insert(infoList);
    }

    // 往该表添加多条记录
    public long insert(List<Plan> infoList) throws SQLException{
        long result = -1;
        mDB.beginTransaction();  // Start the transaction
        try {
            for (Plan info : infoList) {
                ContentValues cv = new ContentValues();
                cv.put("tag", info.tag);
                cv.put("start_time", info.start_time);
                cv.put("end_time", info.end_time);
                cv.put("content", info.content);
                cv.put("finish", info.finish);
                cv.put("breakdown", info.code_b());
                cv.put("schedule", info.code_s());
                result = mDB.insert(TABLE_NAME, "", cv);
                if (result == -1) {
                    throw new SQLException("Failed to insert row");
                }
            }
            mDB.setTransactionSuccessful();  // Mark the transaction as successful
        } finally {
            mDB.endTransaction();  // End the transaction
        }
        return result;
    }

    // 根据条件更新指定的表记录
    public int update(Plan info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("tag", info.tag);
        cv.put("start_time", info.start_time);
        cv.put("end_time", info.end_time);
        cv.put("content", info.content);
        cv.put("finish", info.finish);
        cv.put("breakdown", info.code_b());
        cv.put("schedule", info.code_s());
        // 执行更新记录动作，该语句返回更新的记录数量
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(Plan info) {
        // 执行更新记录动作，该语句返回更新的记录数量
        return update(info, "id=" + info.id);
    }

    // 根据指定条件查询记录，并返回结果数据列表
    public List<Plan> query(String condition) {
        String sql = String.format("select * " +
                "from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: " + sql);
        List<Plan> infoList = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            Plan info = new Plan();
            info.id = cursor.getLong(0);
            info.tag = cursor.getString(1);
            info.start_time = cursor.getString(2);
            info.end_time = cursor.getString(3);
            info.content = cursor.getString(4);
            info.finish = cursor.getString(5);
            info.code_bd = cursor.getString(6);
            info.decode_b();
            info.code_sch = cursor.getString(7);
            info.decode_s();
            infoList.add(info);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return infoList;
    }

    public List<Plan> query() {
        String sql = String.format("select * " +
                "from %s ;", TABLE_NAME);

        List<Plan> infoList = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            Plan info = new Plan();
            info.id = cursor.getLong(0);
            info.tag = cursor.getString(1);
            info.start_time = cursor.getString(2);
            info.end_time = cursor.getString(3);
            info.content = cursor.getString(4);
            info.finish = cursor.getString(5);
            info.code_bd = cursor.getString(6);
            info.decode_b();
            info.code_sch = cursor.getString(7);
            info.decode_s();
            infoList.add(info);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return infoList;
    }
    // Use projections when querying data

    /**
     * Query the database
     * @param projection The columns to return
     * @param selection The columns for the WHERE clause
     * @param selectionArgs The values for the WHERE clause
     * @param sortOrder The sort order
     * @return A list of plans
     */
    public List<Plan> query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        List<Plan> infoList = new ArrayList<>();
        Cursor cursor = mDB.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        while (cursor.moveToNext()) {
            Plan info = new Plan();
            info.id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            info.tag = cursor.getString(cursor.getColumnIndexOrThrow("tag"));
            info.start_time = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
            info.end_time = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));
            info.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            info.finish = cursor.getString(cursor.getColumnIndexOrThrow("finish"));
            info.code_bd = cursor.getString(cursor.getColumnIndexOrThrow("breakdown"));
            info.decode_b();
            info.code_sch = cursor.getString(cursor.getColumnIndexOrThrow("schedule"));
            info.decode_s();
            infoList.add(info);
        }
        cursor.close();
        return infoList;
    }
}
