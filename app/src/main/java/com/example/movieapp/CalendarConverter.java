package com.example.movieapp;

import java.util.Calendar;
import java.util.Date;

public class CalendarConverter {
    private static CalendarConverter instance = null;

    public CalendarConverter() {}

    public static CalendarConverter getInstance() {
        if (instance == null) {
            instance = new CalendarConverter();
        }
        return instance;
    }

    private Date getEmptyDate() {
        return new Date(0);
    }

    public Date getDate(Calendar calendar) {
        Date date = getEmptyDate();
        Date refDate = calendar.getTime();

        date.setYear(refDate.getYear());
        date.setMonth(refDate.getMonth());
        date.setDate(refDate.getDate());

        return date;
    }

    public long getLongDate(Calendar calendar) {
        return getDate(calendar).getTime();
    }

    public Date getTime(Calendar calendar) {
        Date time = getEmptyDate();
        Date refTime = calendar.getTime();

        time.setHours(refTime.getHours());
        time.setMinutes(refTime.getMinutes());
        time.setSeconds(refTime.getSeconds());

        return time;
    }

    public long getLongTime(Calendar calendar) {
        return getTime(calendar).getTime();
    }
}
