package com.example.fu.myapplication.model;

import android.icu.util.LocaleData;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Alarm implements Parcelable {

    public static final int MON = 1;
    public static final int TUES = 2;
    public static final int WED = 3;
    public static final int THURS = 4;
    public static final int FRI = 5;
    public static final int SAT = 6;
    public static final int SUN = 7;

    private int id;
    private long time;
    private SparseBooleanArray daysInWeek;
    private boolean isEnabled;

    public Alarm(int id, long time, SparseBooleanArray daysInWeek, boolean isEnabled) {
        this.id = id;
        this.time = time;
        this.daysInWeek = daysInWeek;
        this.isEnabled = isEnabled;
    }

    public Alarm() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public SparseBooleanArray getDaysInWeek() {
        return daysInWeek;
    }

    public void setDaysInWeek(SparseBooleanArray daysInWeek) {
        this.daysInWeek = daysInWeek;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    private Alarm(Parcel in) {

        id = in.readInt();
        time = in.readLong();
        daysInWeek = in.readSparseBooleanArray();
        isEnabled = in.readByte() != 0;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeLong(time);
        parcel.writeSparseBooleanArray(daysInWeek);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));

    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    public static SparseBooleanArray addDaysInWeekToAlarm(boolean... days) {

        final SparseBooleanArray arrayDay = createDayFalseInWeek();

        for (int i = 1; i <= 7; i++) {
            arrayDay.put(i, days[i - 1]);
        }

        return arrayDay;

    }

    public static SparseBooleanArray createDayFalseInWeek() {

        final SparseBooleanArray arrayDay = new SparseBooleanArray();
        arrayDay.put(MON, false);
        arrayDay.put(TUES, false);
        arrayDay.put(WED, false);
        arrayDay.put(THURS, false);
        arrayDay.put(FRI, false);
        arrayDay.put(SAT, false);
        arrayDay.put(SUN, false);

        return arrayDay;

    }
    //DEMO
    public static List<Alarm> creatAlarmListDEMO(){
        List<Alarm> list= new ArrayList<>();
        list.add(new Alarm(1, 11,addDaysInWeekToAlarm(true,false,true,false,true,false,true),true));
        list.add(new Alarm(2, 18,addDaysInWeekToAlarm(false,true,false,true,false,true,false),true));
        return list;
    }


    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", time=" + time +
                ", daysInWeek=" + daysInWeek +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
