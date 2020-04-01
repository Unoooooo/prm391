package com.example.fu.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fu.myapplication.model.Alarm;
import com.example.fu.myapplication.util.AlarmUtils;

import java.util.ArrayList;
import java.util.List;

public final class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "alarms.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "alarms";

    public static final String COL_ID = "_id";
    public static final String COL_TIME = "time";
    public static final String COL_MON = "mon";
    public static final String COL_TUES = "tues";
    public static final String COL_WED = "wed";
    public static final String COL_THURS = "thurs";
    public static final String COL_FRI = "fri";
    public static final String COL_SAT = "sat";
    public static final String COL_SUN = "sun";
    public static final String COL_IS_ENABLED = "is_enabled";

    //Use Instance for first Load
    private static DataBaseHelper sInstance = null;

    public static DataBaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataBaseHelper(context);
        }
        return sInstance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TIME + " INTEGER NOT NULL, " +
                COL_MON + " INTEGER NOT NULL, " +
                COL_TUES + " INTEGER NOT NULL, " +
                COL_WED + " INTEGER NOT NULL, " +
                COL_THURS + " INTEGER NOT NULL, " +
                COL_FRI + " INTEGER NOT NULL, " +
                COL_SAT + " INTEGER NOT NULL, " +
                COL_SUN + " INTEGER NOT NULL, " +
                COL_IS_ENABLED + " INTEGER NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(CREATE_ALARMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertAlarm(Alarm alarm) {
        return getWritableDatabase().insert(TABLE_NAME, null, AlarmUtils.toContentValues(alarm));
    }

    public int deleteAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        final String where = COL_ID + "=?";
        final String[] whereArgs = new String[]{Long.toString(alarm.getId())};
        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public List<Long> selectTimeEqualsSystem() {
        List<Long> times = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select  * from alarms where is_enabled = 1 ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            times.add(cursor.getLong(cursor.getColumnIndex("time")));
            cursor.moveToNext();
        }

        return times;
    }


    public int updateAlarm(Alarm alarm) {

        SQLiteDatabase db = this.getWritableDatabase();
        final String where = COL_ID + "=?";
        final String[] whereArgs = new String[]{Long.toString(alarm.getId())};
        return db.update(TABLE_NAME, AlarmUtils.toContentValues(alarm), where, whereArgs);
    }

    public List<Alarm> getAlarmArray() {
        List<Alarm> alarmList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            alarmList = AlarmUtils.convertAlarmCursortoList(cursor);
        } catch (Exception e) {

        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
        }
        return alarmList;
    }

    public List<Alarm> getAlarmArrayOn() {
        List<Alarm> alarmList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select  * from alarms where is_enabled = 1 ", null);
            alarmList = AlarmUtils.convertAlarmCursortoList(cursor);
        } catch (Exception e) {

        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
        }
        return alarmList;
    }

    public int selectMaxId() {
        int idMax=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select max("+COL_ID+") from "+TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            idMax = cursor.getInt(0);
            cursor.moveToNext();
        }

        return idMax;
    }

}
