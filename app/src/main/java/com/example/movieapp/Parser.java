package com.example.movieapp;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Parser {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static Date parseDateTime(String dateTime) {
        Date date = null;

        try {
            date = DATE_TIME_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseDate(String sDate) {
        Date date = null;

        try {
            date = DATE_FORMAT.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseTime(String sTime) {
        Date time = null;

        try {
            time = TIME_FORMAT.parse(sTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }
}
