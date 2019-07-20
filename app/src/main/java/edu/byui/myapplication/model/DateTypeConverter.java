package edu.byui.myapplication.model;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    @TypeConverter
    public static Date toDate(int year) {
        Calendar calYear = new GregorianCalendar(year, 1, 1);
        return new Date(calYear.getTimeInMillis());
    }

    @TypeConverter
    public static int toInt(Date date) {
        return date.getYear();
    }

    @TypeConverter
    public static Integer toInteger(Date date) {
        return date.getYear();
    }

    @TypeConverter
    public static Date toDate(Integer year) {
        Calendar calYear = new GregorianCalendar(year, 1, 1);
        return new Date(calYear.getTimeInMillis());
    }



}