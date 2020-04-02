package com.example.fu.myapplication.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.SparseBooleanArray;

import com.example.fu.myapplication.model.Alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static final String[] NAME_DAY_ARRAY = {"MON", "TUES", "WED", "THURS", "FRI", "SAT", "SUN"};

    private static final SimpleDateFormat TIME_FORMAT_HH_MM_12 =
            new SimpleDateFormat("hh:mm", Locale.getDefault());

    private static final SimpleDateFormat TIME_FORMAT_H_MM_12AMPM =
            new SimpleDateFormat("h:mm a", Locale.getDefault());

    private static final SimpleDateFormat AM_PM_FORMAT =
            new SimpleDateFormat("a", Locale.getDefault());

    private static final SimpleDateFormat MINUTE_FORMAT =
            new SimpleDateFormat("mm", Locale.getDefault());

    private static final SimpleDateFormat HOUR_FORMAT =
            new SimpleDateFormat("h", Locale.getDefault());

    private static final SimpleDateFormat DAY_FORMAT =
            new SimpleDateFormat("EEE", Locale.getDefault());

    private static final SimpleDateFormat TIME_FORMAT_HH_MM_24 =
            new SimpleDateFormat("H", Locale.getDefault());


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
        while (!cursorAlarm.isAfterLast()) {
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

    //Hour in am/pm (1-12)
    public static String convertTimeToHour12AmPm(long time) {
        return TIME_FORMAT_H_MM_12AMPM.format(time);
    }

    public static String convertTimeToHour12(long time) {
        return TIME_FORMAT_HH_MM_12.format(time);
    }

    public static String convertTimeToHour24(long time) {
        return TIME_FORMAT_HH_MM_24.format(time);
    }

    //Minute in hour
    public static String convertTimeToAmPm(long time) {
        return AM_PM_FORMAT.format(time);
    }

    public static String convertTimeToMinute(long time) {
        return MINUTE_FORMAT.format(time);
    }

    public static String convertTimeToHour(long time) {
        return HOUR_FORMAT.format(time);
    }

    public static String convertTimeToDay(long time) {
        return DAY_FORMAT.format(time);
    }


    public static Spannable convertSparseBooleanArrayToString(SparseBooleanArray days) {
        SpannableStringBuilder resultDays = new SpannableStringBuilder();
        for (int i = 0; i < days.size(); i++) {
            int startIndex = 0;
            if (resultDays.length() != 0) {
                startIndex = resultDays.length();
            }
            resultDays.append(" " + NAME_DAY_ARRAY[i]);
            int endIndex = resultDays.length();

            if (days.valueAt(i)) {
                resultDays.setSpan(new ForegroundColorSpan(Color.parseColor("#0000ff")), startIndex, endIndex, 0);
            }
        }
        return resultDays;
    }

    public static boolean checkItDayNow(Alarm alarm) {
        SparseBooleanArray sparseBooleanArray = alarm.getDaysInWeek();

        String dayName = convertTimeToDay(Calendar.getInstance().getTimeInMillis());
        switch (dayName) {

            case "Mon":
                if (sparseBooleanArray.get(1)) {
                    return true;
                }
                break;

            case "Tue":
                if (sparseBooleanArray.get(2)){
                    return true;
                }
break;
            case "Wed":
                if (sparseBooleanArray.get(3)){
                    return true;
                }break;

            case "Thu":
                if (sparseBooleanArray.get(4)) {
                    return true;
                }
                break;

            case "Fri":
                if (sparseBooleanArray.get(5)){
                    return true;
                }
                break;

            case "Sat":
                if (sparseBooleanArray.get(6)) {
                    return true;
                }
                break;

            case "Sun":
                if (sparseBooleanArray.get(7)){
                    return true;
                }
                break;

        }


        return false;
    }

    public static boolean checkFalseDayAll(boolean ...days){
        for (int i = 0; i < days.length; i++) {
                if(days[i]){
                    return true;
                }
        }
        return false;
    }



}
