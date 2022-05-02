package com.example.movieapp;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Parser {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Date Formats

    public static final String DATE_FORMAT_XML = "yyyy-MM-dd";
    public static final String DATE_FORMAT_XML_EMPTY = "%04d-%02d-%02d";
    public static final String DATE_FORMAT_HUM = "dd.MM.yyyy";
    public static final String DATE_FORMAT_HUM_EMPTY = "%02d.%02d.%04d";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Time Formats

    public static final String TIME_FORMAT_L = "HH:mm:ss";
    public static final String TIME_FORMAT_L_EMPTY = "%02d:%02d:%02d";
    public static final String TIME_FORMAT_S = "HH:mm";
    public static final String TIME_FORMAT_S_EMPTY = "%02d:%02d";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DateTime Formats

    public static final String DATE_TIME_FORMAT_XML = DATE_FORMAT_XML + "'T'" + TIME_FORMAT_L;
    public static final String DATE_TIME_FORMAT_HUM = DATE_FORMAT_HUM + " " + TIME_FORMAT_S;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Date Formatters

    public static final SimpleDateFormat DATE_FORMATTER_XML = new SimpleDateFormat(DATE_FORMAT_XML); // XML style
    public static final SimpleDateFormat DATE_FORMATTER_HUM = new SimpleDateFormat(DATE_FORMAT_HUM); // human style


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Time Formatters

    public static final SimpleDateFormat TIME_FORMATTER_XML = new SimpleDateFormat(TIME_FORMAT_L);
    public static final SimpleDateFormat TIME_FORMATTER_HUM = new SimpleDateFormat(TIME_FORMAT_S);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DateTime Formatters

    public static final SimpleDateFormat DATE_TIME_FORMATTER_XML = new SimpleDateFormat(DATE_TIME_FORMAT_XML);
    public static final SimpleDateFormat DATE_TIME_FORMATTER_HUM = new SimpleDateFormat(DATE_TIME_FORMAT_HUM);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods

    public static Date parseDateTime(String dateTime) {
        Date date = null;

        try {
            date = DATE_TIME_FORMATTER_XML.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseDate(String sDate) {
        Date date = null;

        try {
            date = DATE_FORMATTER_HUM.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseTime(String sTime) {
        Date time = null;

        try {
            time = TIME_FORMATTER_HUM.parse(sTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }
}
