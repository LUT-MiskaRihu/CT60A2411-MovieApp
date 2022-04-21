package com.example.movieapp;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Date parseDateTime(String dateTime) {
        Date date = null;
        try {
            date = dtFormatter.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
