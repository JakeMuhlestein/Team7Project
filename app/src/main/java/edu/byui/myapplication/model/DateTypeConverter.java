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
    public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        return utilDate == null ? null : new java.sql.Date(utilDate.getTime());
    }

    @TypeConverter
    public static java.util.Date toUtilDate(java.sql.Date sqlDate) {
        return sqlDate == null ? null : new java.util.Date(sqlDate.getTime());
    }



}