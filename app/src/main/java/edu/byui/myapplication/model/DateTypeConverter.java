package edu.byui.myapplication.model;

import androidx.room.TypeConverter;

import java.sql.Date;

/**
 * Needed for ROOM to convert between dates and longs
* */
public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {

        return date == null ? null : date.getTime();
    }
}