package com.example.movieapp;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class CalendarConverter {
    private static final String sClassTag = "CalendarConverter";

    /**
     * This method returns an "empty" reference date.
     * @return "empty" reference date (Date object).
     */
    public static Date getEmptyDate() {
        return Parser.parseDateTime("2000-01-01T00:00:00");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Convert to Date String (dd.MM.yyyy)

    /**
     * Receives a Calendar object,
     * extracts the date information (hours and minutes),
     * and returns a date string in the format specified in the Parser class.
     */
    @NonNull
    @SuppressLint("DefaultLocale")
    public static String convertToDateString(@NonNull Calendar calendar) {
        final String sMethodTag = "convertToDateString(Calendar)";

        int iDay = calendar.get(Calendar.DAY_OF_MONTH);
        int iMonth = calendar.get(Calendar.MONTH) + 1;
        int iYear = calendar.get(Calendar.YEAR);
        String sDate = String.format(Parser.DATE_FORMAT_HUM_EMPTY, iDay, iMonth, iYear);

        Log.d((sClassTag + "." + sMethodTag), ("Returning sDate with the value '" + sDate + "'."));
        return sDate;
    }

    /**
     * Receives a Date object,
     * extracts the date information (hours and minutes),
     * and returns a date string in the format specified in the Parser class.
     */
    @NonNull
    @SuppressLint("DefaultLocale")
    public static String convertToDateString(@NonNull Date date) {
        final String sMethodTag = "convertToDateString(Date)";

        int iDay = date.getDate();
        int iMonth = date.getMonth() + 1;
        int iYear = date.getYear() + 1900;
        String sDate = String.format(Parser.DATE_FORMAT_HUM_EMPTY, iDay, iMonth, iYear);

        Log.d(sClassTag + "." + sMethodTag, "Returning sDate with the value '" + sDate + "'.");
        return sDate;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Convert to Time String (HH:mm)

    /**
     * Receives a Calendar object,
     * extracts the time information (hours and minutes),
     * and returns a time string in the format specified in the Parser class.
     */
    @NonNull
    @SuppressLint("DefaultLocale")
    public static String convertToTimeString(@NonNull Calendar calendar) {
        final String sMethodTag = "convertToDateString(Calendar)";

        int iHour = calendar.get(Calendar.HOUR_OF_DAY);
        int iMinute = calendar.get(Calendar.MINUTE) + 1;
        String sTime = String.format(Parser.TIME_FORMAT_S_EMPTY, iHour, iMinute);

        Log.d(sClassTag + "." + sMethodTag, "Returning sTime with the value '" + sTime + "'.");
        return sTime;
    }

    /**
     * Receives a Date object,
     * extracts the time information (hours and minutes),
     * and returns a time string in the format specified in the Parser class.
     */
    @NonNull
    @SuppressLint("DefaultLocale")
    public static String convertToTimeString(@NonNull Date date) {
        final String sMethodTag = "convertToDateString(Date)";

        int iHour = date.getHours();
        int iMinute = date.getMinutes();
        String sTime = String.format(Parser.TIME_FORMAT_S_EMPTY, iHour, iMinute);

        Log.d(sClassTag + "." + sMethodTag, "Returning sTime with the value '" + sTime + "'.");
        return sTime;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Extract Date Information

    /**
     * Receives a Calendar object,
     * extracts the date information,
     * and returns the extracted information as a Date object.
     */
    public static Date extractDateAsDate(@NonNull Calendar calendar) {
        String sDate = convertToDateString(calendar);
        return Parser.parseDate(sDate);
    }

    /**
     * Receives a Date object,
     * extracts the date information,
     * and returns the extracted information as a Date object.
     */
    public static Date extractDateAsDate(@NonNull Date date) {
        String sDate = convertToDateString(date);
        return Parser.parseDate(sDate);
    }

    /**
     * Receives a Calendar object,
     * extracts the date information,
     * and returns the extracted information as a long integer.
     * (Useful for comparing only the date values of Calendar and/or Date objects.)
     */
    public static long extractDateAsLong(@NonNull Calendar calendar) {
        final String sMethodTag = "extractDateAsLong(Calendar)";
        long lDate = extractDateAsDate(calendar).getTime();
        Log.d(sClassTag + "." + sMethodTag, "Returning lDate with the value " + lDate + ".");
        return lDate;
    }

    /**
     * Receives a Date object,
     * extracts the date information,
     * and returns the extracted information as a long integer.
     * (Useful for comparing only the date values of Calendar and/or Date objects.)
     */
    public static long extractDateAsLong(@NonNull Date date) {
        final String sMethodTag = "extractDateAsLong(Date)";
        long lDate = extractDateAsDate(date).getTime();
        Log.d(sClassTag + "." + sMethodTag, "Returning lDate with the value " + lDate + ".");
        return lDate;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Extract Time Information

    /**
     * Receives a Calendar object,
     * extracts the time information,
     * and returns the extracted information as a Date object.
     */
    public static Date extractTimeAsDate(@NonNull Calendar calendar) {
        String sTime = convertToTimeString(calendar);
        return Parser.parseTime(sTime);
    }

    /**
     * Receives a Date object,
     * extracts the time information,
     * and returns the extracted information as a Date object.
     */
    public static Date extractTimeAsDate(@NonNull Date date) {
        String sTime = convertToTimeString(date);
        return Parser.parseTime(sTime);
    }

    /**
     * Receives a Calendar object,
     * extracts the time information,
     * and returns the extracted information as a long integer.
     * (Useful for comparing only the time values of Calendar and/or Date objects.)
     */
    public static long extractTimeAsLong(@NonNull Calendar calendar) {
        final String sMethodTag = "extractTimeAsLong(Calendar)";
        long lTime = extractTimeAsDate(calendar).getTime();
        Log.d(sClassTag + "." + sMethodTag, "Returning lTime with the value " + lTime + ".");
        return lTime;
    }

    /**
     * Receives a Date object,
     * extracts the time information,
     * and returns the extracted information as a long integer.
     * (Useful for comparing only the time values of Calendar and/or Date objects.)
     */
    public static long extractTimeAsLong(@NonNull Date date) {
        final String sMethodTag = "extractTimeAsLong(Date)";
        long lTime = extractTimeAsDate(date).getTime();
        Log.d(sClassTag + "." + sMethodTag, "Returning lTime with the value " + lTime + ".");
        return lTime;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
}
