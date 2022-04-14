package com.example.test;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import java.util.Calendar;

@Deprecated
public class Filter1 {

    /**
     * Calendars
     */
    public static final int CALENDAR_START_MIN = 0;
    public static final int CALENDAR_START_MAX = 1;
    public static final int CALENDAR_START_BOTH = 2;


    /**
     * Error Codes
     */
    public static final String ERR_CALENDAR_NOT_FOUND = "Specified calendar not found: ";

    /**
     *
     */
    private static Calendar startTimeMin;
    private static Calendar startTimeMax;
    private static String title;
    private int location;

    private static Filter1 instance = null;

    public Filter1() { }

    /**
     *
     * @return instance; if instance doesn't exist, create one first
     */
    public static Filter1 getInstance() {
        if (instance == null) {
            instance = new Filter1();
            startTimeMin = Calendar.getInstance();
            startTimeMin.set(1, 1, 1);
            startTimeMax = Calendar.getInstance();
            startTimeMax.set(1, 1, 1);
        }
        return instance;
    }

    /**
     * Takes own calendar, params for the calendars
     * @param calendar calendar to modify {this.dateTimeMin | this.dateTimeMax | both}
     * @param year new year
     * @param month new month {int from 0 to 11 inclusive}
     * @param day new day of month
     */
    private void setDate(@NonNull Calendar calendar, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(year, month, day);
    }

    /**
     *
     * @param calendar
     * @param hour
     * @param minute
     */
    private void setTime(@NonNull Calendar calendar, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
    }

    /**
     *
     * @param calendar which calendar to modify, {CALENDAR_START_MIN | CALENDAR_START_MAX | CALENDAR_START_BOTH}
     * @param year new value for year {int}
     * @param month new value for month {int from 0 to 11 inclusive}
     * @param day new value for day of month {int from 1 to 31 inclusive}
     */
    public void setDate(int calendar, int year, int month, int day) {
        switch (calendar) {
            case (CALENDAR_START_MIN):
                setDate(startTimeMin, year, month, day);
                break;
            case (CALENDAR_START_MAX):
                setDate(startTimeMax, year, month, day);
                break;
            case (CALENDAR_START_BOTH):
                setDate(startTimeMin, year, month, day);
                setDate(startTimeMax, year, month, day);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendar);
                break;
        }
    }

    /**
     *
     * @param calendar
     * @param hour
     * @param minute
     */
    public void setTime(int calendar, int hour, int minute) {
        switch (calendar) {
            case (CALENDAR_START_MIN):
                setTime(startTimeMin, hour, minute);
                break;
            case (CALENDAR_START_MAX):
                setTime(startTimeMax, hour, minute);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendar);
                break;
        }
    }

    /**
     *
     * @param calendar which calendar to return, {CALENDAR_START_MIN | CALENDAR_START_MAX}
     * @return specified calendar, {dateTimeMin if calendar == CALENDAR_START_MIN | dateTimeMax if calendar == CALENDAR_START_MAX | null if calendar is something else}
     */
    public Calendar getCalendar(int calendar) {
        Calendar outputCalendar = null;
        switch (calendar) {
            case (CALENDAR_START_MIN):
                outputCalendar = startTimeMin;
                break;
            case (CALENDAR_START_MAX):
                outputCalendar = startTimeMax;
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendar);
                break;
        }
        return outputCalendar;
    }

    public int getYearMin() {
        return startTimeMin.get(Calendar.HOUR_OF_DAY);
    }

    public int getYearMax() {
        return startTimeMax.get(Calendar.HOUR_OF_DAY);
    }

    public int getMonthMin() {
        return startTimeMin.get(Calendar.MONTH);
    }

    public int getMonthMax() {
        return startTimeMax.get(Calendar.MONTH);
    }

    public int getDayMin() {
        return startTimeMin.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayMax() {
        return startTimeMax.get(Calendar.DAY_OF_MONTH);
    }

    public int getHourMin() {
        return startTimeMin.get(Calendar.HOUR_OF_DAY);
    }

    public int getHourMax() {
        return startTimeMax.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinuteMin() {
        return startTimeMin.get(Calendar.MINUTE);
    }

    public int getMinuteMax() {
        return startTimeMax.get(Calendar.MINUTE);
    }

    public void setTitle(String title) {
        Filter1.title = title;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        String s;
        s = "\n\nFILTER\n"
                + "Title: %s\n"
                + "Start Time (min): %02d.%02d.%04d at %02d:%02d\n"
                + "Start Time (max): %02d.%02d.%04d at %02d:%02d\n"
                + "Location: %04d: %s";

        s = String.format(
                s,
                title,
                getDayMin(),
                getMonthMin(),
                getYearMin(),
                getHourMin(),
                getMinuteMin(),
                getDayMax(),
                getMonthMax(),
                getYearMax(),
                getHourMax(),
                getMinuteMax(),
                location,
                "Location name placeholder"
        );

        return s;
    }
}


