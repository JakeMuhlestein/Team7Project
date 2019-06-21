package edu.byui.myapplication.model;

import androidx.room.TypeConverter;

import java.sql.Date;

/**
 * Needed for ROOM to convert between dates and longs
* */
public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}