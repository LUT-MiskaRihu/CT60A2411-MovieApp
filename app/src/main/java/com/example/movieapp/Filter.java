package com.example.movieapp;

import android.annotation.SuppressLint;

import androidx.annotation.Discouraged;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

public class Filter {

    /**
     * Calendar Codes
     */
    public static final int START_TIME_MIN = 0;     // represents startDateTimeMin
    public static final int START_TIME_MAX = 1;     // represents startDateTimeMax
    public static final int START_TIME_BOTH = 2;

    /**
     * Field Codes
     */
    public static final int FIELD_TITLE = 0;
    public static final int FIELD_DATE = 1;
    public static final int FIELD_TIME_MIN = 2;
    public static final int FIELD_TIME_MAX = 3;

    /**
     * Error Messages
     */
    public static final String ERR_CALENDAR_NOT_FOUND = "Specified calendar not found: ";
    public static final String ERR_FIELD_NOT_FOUND = "Specified field not found: ";

    /**
     * Attributes
     */
    private static Calendar startDateTimeMin;   // Stores minimum start time; year, month, day, hour, and minute
    private static Calendar startDateTimeMax;   // Stores maximum start time; year, month, day, hour, and minute
    private static String title;
    private static int locationID;                // stores the location id of a theater

    private static Filter instance = null;

    private Filter() { }

    /**
     * Gets the instance of the Filter class.
     * @return instance; if instance doesn't exist, create one first
     */
    public static Filter getInstance() {
        if (instance == null) {
            instance = new Filter();
            clearTitle();
            startDateTimeMin = Calendar.getInstance();
            clearStartDateMin();
            clearStartTimeMin();
            startDateTimeMax = Calendar.getInstance();
            clearStartDateMax();
            clearStartTimeMax();
        }
        return instance;
    }

    /**
     * Sets the date for the chosen calendar.
     * @param calendar calendar to modify {startDateTimeMin | startDateTimeMax}
     * @param year new year
     * @param month new month {int from 0 to 11 inclusive}
     * @param day new day of month
     */
    private void setDate(@NonNull Calendar calendar, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    /**
     * Sets the time for the chosen calendar.
     * @param calendar
     * @param hour
     * @param minute
     */
    private void setTimeFor(@NonNull Calendar calendar, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
    }

    /**
     * Public method that allows setting the date for the calendar specified by calendar code.
     * @param calendar which calendar to modify, {CALENDAR_START_MIN | CALENDAR_START_MAX | CALENDAR_START_BOTH}
     * @param year new value for year {int}
     * @param month new value for month {int from 0 to 11 inclusive}
     * @param day new value for day of month {int from 1 to 31 inclusive}
     */
    public void setDateFor(int calendar, int year, int month, int day) {
        switch (calendar) {
            case (START_TIME_MIN):
                setDate(startDateTimeMin, year, month, day);
                break;
            case (START_TIME_MAX):
                setDate(startDateTimeMax, year, month, day);
                break;
            case (START_TIME_BOTH):
                setDate(startDateTimeMin, year, month, day);
                setDate(startDateTimeMax, year, month, day);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendar);
                break;
        }
    }

    /**
     * Public method that allows setting the time for the calendar specified by calendar code.
     * @param calendar which calendar to modify, {CALENDAR_START_MIN | CALENDAR_START_MAX | CALENDAR_START_BOTH}
     * @param hour new value for hour {int from 0 to 23 inclusive}
     * @param minute new value for minute {int from 0 to 59 inclusive}
     */
    public void setTimeFor(int calendar, int hour, int minute) {
        switch (calendar) {
            case (START_TIME_MIN):
                setTimeFor(startDateTimeMin, hour, minute);
                break;
            case (START_TIME_MAX):
                setTimeFor(startDateTimeMax, hour, minute);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendar);
                break;
        }
    }

    @Discouraged(message = "Use methods 'getYearFrom(int calendarCode)' etc.")
    /**
     * Returns the specified calendar. 
     * @param calendar which calendar to return, {CALENDAR_START_MIN | CALENDAR_START_MAX}
     * @return specified calendar, {dateTimeMin if calendar == CALENDAR_START_MIN | dateTimeMax if calendar == CALENDAR_START_MAX | null if calendar is something else}
     */
    public Calendar getCalendar(int calendar) {
        Calendar outputCalendar = null;
        switch (calendar) {
            case (START_TIME_MIN):
                outputCalendar = startDateTimeMin;
                break;
            case (START_TIME_MAX):
                outputCalendar = startDateTimeMax;
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendar);
                break;
        }
        return outputCalendar;
    }

    /**
     * Public method for getting the YEAR field of a calendar specified by the calendar code.
     * @return year when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    public int getYearFrom(int calendarCode) {
        int year = -1;
        switch (calendarCode) {
            case (START_TIME_MIN):
                year = startDateTimeMin.get(Calendar.YEAR);
                break;
            case (START_TIME_MAX):
                year = startDateTimeMax.get(Calendar.YEAR);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendarCode);
                break;
        }
        return year;
    }

    /**
     * Public method for getting the MONTH field of a calendar specified by the calendar code.
     * @return month when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    public int getMonthFrom(int calendarCode) {
        int month = -1;
        switch (calendarCode) {
            case (START_TIME_MIN):
                month = startDateTimeMin.get(Calendar.MONTH) + 1;
                break;
            case (START_TIME_MAX):
                month = startDateTimeMax.get(Calendar.MONTH) + 1;
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendarCode);
                break;
        }
        return month;
    }

    /**
     * Public method for getting the DAY OF MONTH field of a calendar specified by the calendar code.
     * @return day when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    public int getDayFrom(int calendarCode) {
        int day = -1;
        switch (calendarCode) {
            case (START_TIME_MIN):
                day = startDateTimeMin.get(Calendar.DAY_OF_MONTH);
                break;
            case (START_TIME_MAX):
                day = startDateTimeMax.get(Calendar.DAY_OF_MONTH);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendarCode);
                break;
        }
        return day;
    }

    /**
     * Public method for getting the HOUR OF DAY field of a calendar specified by the calendar code.
     * @return hour when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    public int getHourFrom(int calendarCode) {
        int hour = -1;
        switch (calendarCode) {
            case (START_TIME_MIN):
                hour = startDateTimeMin.get(Calendar.HOUR_OF_DAY);
                break;
            case (START_TIME_MAX):
                hour = startDateTimeMax.get(Calendar.HOUR_OF_DAY);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendarCode);
                break;
        }
        return hour;
    }

    /**
     * Public method for getting the MINUTE field of a calendar specified by the calendar code.
     * @return minute when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    public int getMinuteFrom(int calendarCode) {
        int minute = -1;
        switch (calendarCode) {
            case (START_TIME_MIN):
                minute = startDateTimeMin.get(Calendar.MINUTE);
                break;
            case (START_TIME_MAX):
                minute = startDateTimeMax.get(Calendar.MINUTE);
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND + calendarCode);
                break;
        }
        return minute;
    }

    /**
     * Converts Calendar object's date & time information to a long integer.
     * @param calendar calendar code
     * @return calendar date & time info as long
     */
    private long getTime(int calendar) {
        Calendar dateTime = Calendar.getInstance();
        switch (calendar) {
            case (START_TIME_MIN):
                dateTime = startDateTimeMin;
                break;
            case (START_TIME_MAX):
                dateTime = startDateTimeMax;
                break;
            default:
                System.err.println(ERR_CALENDAR_NOT_FOUND);
                break;
        }
        return dateTime.getTime().getTime();
    }

    public void setTitle(String title) {
        Filter.title = title;
    }

    public void setLocationID(int id) {
        locationID = id;
    }

    public void setLocationID(String name) {
        locationID = Database.getInstance().getTheatreByName(name);
    }

    public int getLocationID() {
        return locationID;
    }

    /**
     * Resets the title field.
     */
    private static void clearTitle() {
        title = null;
    }

    /**
     * Resets the date of starDateTimeMin to current date.
     */
    private static  void clearStartDateMin() {
        startDateTimeMin.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        startDateTimeMin.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        startDateTimeMin.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Resets the date of startDateTimeMax to the maximum value, i.e., 31.12.3000
     * 3000 is just an arbitrarily large number. I chose 3000 because I don't think anyone will
     * be using this app in the year 3000 anymore :D
     */
    private static void clearStartDateMax() {
        startDateTimeMax.set(Calendar.YEAR, 3000);
        startDateTimeMax.set(Calendar.MONTH, 11);
        startDateTimeMax.set(Calendar.DAY_OF_MONTH, 31);
    }

    /**
     * Resets the date of starDateTimeMin to current time.
     */
    public static void clearStartTimeMin() {
        startDateTimeMin.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        startDateTimeMin.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
        startDateTimeMin.set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND));
    }

    /**
     * Resets the time of startDateTimeMax to the maximum value, i.e., 23:59:59
     */
    public static void clearStartTimeMax() {
        startDateTimeMax.set(Calendar.HOUR_OF_DAY, 23);
        startDateTimeMax.set(Calendar.MINUTE, 59);
        startDateTimeMax.set(Calendar.SECOND, 59);
    }

    /**
     * Clears or resets the field specified by field code
     * (aka resets the attribute value from the filter).
     * @param fieldCode
     */
    public void clearField(int fieldCode) {
        switch (fieldCode) {
            case (FIELD_TITLE):
                clearTitle();
                break;
            case (FIELD_DATE):
                clearStartDateMin();
                clearStartDateMax();
                break;
            case (FIELD_TIME_MIN):
                clearStartTimeMin();
                break;
            case (FIELD_TIME_MAX):
                clearStartTimeMax();
                break;
            default:
                System.err.println(ERR_FIELD_NOT_FOUND + fieldCode);
        }
    }

    @NonNull
    @SuppressLint({"DefaultLocale", "DiscouragedApi"})
    @Override
    public String toString() {
        String s;
        s = "#### Filter.toString() ######################################\n"
                + "\t\tTitle: '%s'\n"
                + "\t\tMinimum Start Time: '%02d.%02d.%04d %02d:%02d' or %d\n"
                + "\t\tMaximum Start Time: '%02d.%02d.%04d %02d:%02d' or %d\n"
                + "\t\tLocation: %04d or '%s'\n"
                + "#############################################################";

        s = String.format(
                s,
                title,
                getDayFrom(START_TIME_MIN),
                getMonthFrom(START_TIME_MIN),
                getYearFrom(START_TIME_MIN),
                getHourFrom(START_TIME_MIN),
                getMinuteFrom(START_TIME_MIN),
                getCalendar(START_TIME_MIN).getTime().getTime(),
                getDayFrom(START_TIME_MAX),
                getMonthFrom(START_TIME_MAX),
                getYearFrom(START_TIME_MAX),
                getHourFrom(START_TIME_MAX),
                getMinuteFrom(START_TIME_MAX),
                getCalendar(START_TIME_MAX).getTime().getTime(),
                locationID,
                "Location name placeholder"
        );

        return s;
    }

    public ArrayList<Show> getShows() {
        System.out.println("#### Filter.getShows() ######################################");
        ArrayList<Show> filtered = new ArrayList<>();
        for (Show show : Database.getInstance().getShows()) {
            boolean matchesCriteria = true;
            // Filter by theatre, 1029 means all locations, > 1029 means a specific location is selected
//            if (locationID > 1029) {
//                if (show.getLocationID() != locationID) {
//                    filtered.remove(show);
//                }
//            }

            // Filter by title
            if (title != null) {
                String t1 = show.getTitle().toUpperCase();
                String t2 = title.toUpperCase();
                if (!t1.contains(t2)) {
                    matchesCriteria = false;
                }
            }

            // Filter by date and time
            long min = getTime(START_TIME_MIN);
            long max = getTime(START_TIME_MAX);
            long test = show.getStartTime();
            if (test < min) {
                matchesCriteria = false;
            }
            if (test > max) {
                matchesCriteria = false;
            }
            if (matchesCriteria) {
                filtered.add(show);
            }
        }

        System.out.println("#############################################################");
        return filtered;
    }
}


