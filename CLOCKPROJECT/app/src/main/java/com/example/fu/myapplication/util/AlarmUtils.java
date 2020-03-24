package com.example.fu.myapplication.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.SparseBooleanArray;

import com.example.fu.myapplication.model.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.fu.myapplication.data.DataBaseHelper.COL_FRI;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_ID;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_IS_ENABLED;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_MON;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_SAT;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_SUN;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_THURS;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_TIME;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_TUES;
import static com.example.fu.myapplication.data.DataBaseHelper.COL_WED;


public final class AlarmUtils {


//    private static final SimpleDateFormat TIME_FORMAT =
//            new SimpleDateFormat("h:mm", Locale.getDefault());
//    private static final SimpleDateFormat AM_PM_FORMAT =
//            new SimpleDateFormat("a", Locale.getDefault());


    public static ContentValues toContentValues(Alarm alarm) {

        final ContentValues contentValues = new ContentValues();

        contentValues.put(COL_TIME, alarm.getTime());

        final SparseBooleanArray days = alarm.getDaysInWeek();
        contentValues.put(COL_MON, days.get(Alarm.MON) ? 1 : 0);
        contentValues.put(COL_TUES, days.get(Alarm.TUES) ? 1 : 0);
        contentValues.put(COL_WED, days.get(Alarm.WED) ? 1 : 0);
        contentValues.put(COL_THURS, days.get(Alarm.THURS) ? 1 : 0);
        contentValues.put(COL_FRI, days.get(Alarm.FRI) ? 1 : 0);
        contentValues.put(COL_SAT, days.get(Alarm.SAT) ? 1 : 0);
        contentValues.put(COL_SUN, days.get(Alarm.SUN) ? 1 : 0);

        contentValues.put(COL_IS_ENABLED, alarm.isEnabled());

        return contentValues;

    }

    public static List<Alarm> convertAlarmCursortoList(Cursor cursorAlarm) {

        if (cursorAlarm == null) return new ArrayList<>();
        List<Alarm> alarms = new ArrayList<>();
        cursorAlarm.moveToFirst();
        while (cursorAlarm.isAfterLast() == false) {
            int id = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_ID));
            long time = cursorAlarm.getLong(cursorAlarm.getColumnIndex(COL_TIME));
            boolean mon = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_MON)) == 1;
            boolean tues = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_TUES)) == 1;
            boolean wed = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_WED)) == 1;
            boolean thurs = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_THURS)) == 1;
            boolean fri = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_FRI)) == 1;
            boolean sat = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_SAT)) == 1;
            boolean sun = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_SUN)) == 1;
            boolean isEnabled = cursorAlarm.getInt(cursorAlarm.getColumnIndex(COL_IS_ENABLED)) == 1;

            SparseBooleanArray daysInWeek = Alarm.addDaysInWeekToAlarm(mon, tues, wed, thurs, fri, sat, sun);
            Alarm alarm = new Alarm(id, time, daysInWeek, isEnabled);
            alarms.add(alarm);
            //next line cursor
            cursorAlarm.moveToNext();
        }
        return alarms;

    }
}
