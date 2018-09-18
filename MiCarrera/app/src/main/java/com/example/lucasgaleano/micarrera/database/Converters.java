package com.example.lucasgaleano.micarrera.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        if(value == null)
            return null;
        else{
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(value);
            return cal;
        }
    }

    @TypeConverter
    public static Long dateToTimestamp(Calendar cal) {
        return cal == null ? null : cal.getTimeInMillis();
    }
}