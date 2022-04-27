package com.example.movieapp;

import android.annotation.SuppressLint;
import java.util.Calendar;
import java.util.Date;

public class CalendarConverter {
    private static CalendarConverter instance = null;

    private CalendarConverter() {}

    public static CalendarConverter getInstance() {
        if (instance == null) {
            instance = new CalendarConverter();
        }
        return instance;
    }

    private static Date getEmptyDate() {
        return new Date(0);
    }

    public static Date extractDateInfo(Calendar calendar) {
        Date date = getEmptyDate();
        Date refDate = calendar.getTime();

        date.setYear(refDate.getYear());
        date.setMonth(refDate.getMonth());
        date.setDate(refDate.getDate());

        return date;
    }

    public static Date extractTimeInfo(Calendar calendar) {
        Date time = getEmptyDate();
        Date refTime = calendar.getTime();

        time.setHours(refTime.getHours());
        time.setMinutes(refTime.getMinutes());
        time.setSeconds(refTime.getSeconds());

        return time;
    }

    public static long convertToLong(Calendar calendar) {
        return calendar.getTime().getTime();
    }

    public static long extractDateAsLong(Calendar calendar) {
        return extractDateInfo(calendar).getTime();
    }

    public static long extractTimeAsLong(Calendar calendar) {
        return extractTimeInfo(calendar).getTime();
    }

    @SuppressLint("DefaultLocale")
    public static String convertToDateString(Date date) {
        int iDay = date.getDate();
        int iMonth = date.getMonth() + 1;
        int iYear = date.getYear() + 1900;
        return String.format("%02d.%02d.%04d", iDay, iMonth, iYear);
    }

    public static String convertToDateString(Calendar calendar) {
        Date date = extractDateInfo(calendar);
        return convertToDateString(date);
    }

    @SuppressLint("DefaultLocale")
    public static String convertToTimeString(Date date) {
        int iHour = date.getHours();
        int iMinute = date.getMinutes();
        return String.format("%02d:%02d", iHour, iMinute);
    }

    public static String convertToTimeString(Calendar calendar) {
        Date time = extractTimeInfo(calendar);
        //Log.v("test", calendar.toString());
        //Log.v("test", time.toString());
        return convertToTimeString(time);
    }
}
