package com.example.movieapp;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Discouraged;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Filter {
    private static final String sClassTag = "Filter";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Other Codes

    public static final int NOT_SET = -1;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Location Codes

    private static final int ANY_LOCATION = 1029; // 1029 refers to theater name "Valitse alue/teatteri" aka all possible locations.

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Calendar Codes

    public static final int START_DT_MIN = 0;     // represents startDateTimeMin
    public static final int START_DT_MAX = 1;     // represents startDateTimeMax
    public static final int START_DT_BOTH = 2;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Field Codes

    public static final int FIELD_TITLE = 0;
    public static final int FIELD_DATE = 1;
    public static final int FIELD_TIME_MIN = 2;
    public static final int FIELD_TIME_MAX = 3;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes

    private int iTheatreID;      // stores the location id of a theater
    private String sTitle;

    private long lMinStartDate;
    private long lMinStartTime;
    private long lMaxStartTime;

    private Calendar calMinStartDate; // Stores minimum start date (day, month, year)
    private Calendar calMinStartTime; // Stores minimum start time (hour and minute)
    private Calendar calMaxStartTime; // Stores maximum start time (hour and minute)

    private static Filter instance = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor and getInstance

    private Filter() {
        iTheatreID = NOT_SET;
        sTitle = null;
        lMinStartDate = NOT_SET;
        lMinStartTime = NOT_SET;
        lMaxStartTime = NOT_SET;
    }

    public static Filter getInstance() {
        if (instance == null) {
            instance = new Filter();
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters, Getters and Clear Functions

    // Theatre ID
    public void setTheatre(int iTheatreID) {
        instance.iTheatreID = iTheatreID;
    }

    public void setTheatre(String sTheatreName) {
        instance.iTheatreID = Database.getTheatre(sTheatreName).getID();
    }

    public int getTheatreID() {
        return instance.iTheatreID;
    }

    public void clearTheatreID() {
        instance.iTheatreID = NOT_SET;
    }

    // Title
    public void setTitle(String sTitle) {
        instance.sTitle = sTitle;
    }

    public String getTitle() {
        return instance.sTitle;
    }

    public void clearTitle() {
        instance.sTitle = null;
    }

    // Date
    public void setMinStartDate(long lMinStartDate) {
        instance.lMinStartDate = lMinStartDate;
    }

    public void setMinStartDate(@NonNull Date dateMinStartDate) {
        instance.lMinStartDate = dateMinStartDate.getTime();
    }

    public void setMinStartDate(String sMinStartDate) {
        setMinStartDate(Parser.parseDate(sMinStartDate));
    }

    public long getMinStartDate() {
        return instance.lMinStartDate;
    }

    public void clearMinStartDate() { instance.lMinStartDate = NOT_SET; }

    // Min Time
    public void setMinStartTime(long lMinStartTime) {
        instance.lMinStartTime = lMinStartTime;
    }

    public void setMinStartTime(@NonNull Date dateMinStartTime) {
        instance.lMinStartTime = dateMinStartTime.getTime();
    }

    public void setMinStartTime(String sMinStartTime) {
        setMinStartTime(Parser.parseTime(sMinStartTime));
    }

    public long getMinStartTime() {
        return instance.lMinStartTime;
    }

    public void clearMinStartTime() { instance.lMaxStartTime = NOT_SET; }

    // Max Time
    public void setMaxStartTime(long lMaxStartTime) {
        instance.lMaxStartTime = lMaxStartTime;
    }

    public void setMaxStartTime(@NonNull Date dateMaxStartTime) {
        instance.lMaxStartTime = dateMaxStartTime.getTime();
    }

    public void setMaxStartTime(String sMaxStartTime) {
        setMaxStartTime(Parser.parseTime(sMaxStartTime));
    }

    public long getMaxStartTime() {
        return instance.lMaxStartTime;
    }

    public void clearMaxStartTime() { instance.lMaxStartTime = NOT_SET; }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Sets the date for the chosen calendar.
     * @param calendar calendar to modify {startDateTimeMin | startDateTimeMax}
     * @param year new year
     * @param month new month {int from 0 to 11 inclusive}
     * @param day new day of month
     */
    @Deprecated
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
    @Deprecated
    private void setTimeFor(@NonNull Calendar calendar, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
    }

    /**
     * Public method that allows setting the date for the iCalendarCode specified by iCalendarCode code.
     * @param iCalendarCode which iCalendarCode to modify, {CALENDAR_START_MIN | CALENDAR_START_MAX | CALENDAR_START_BOTH}
     * @param year new value for year {int}
     * @param month new value for month {int from 0 to 11 inclusive}
     * @param day new value for day of month {int from 1 to 31 inclusive}
     */
    @Deprecated
    public void setDateFor(int iCalendarCode, int year, int month, int day) {
        String sMethodTag = "setDateFor";
        
        switch (iCalendarCode) {
            case (START_DT_MIN):
                setDate(calMinStartTime, year, month, day);
                break;
            case (START_DT_MAX):
                setDate(calMaxStartTime, year, month, day);
                break;
            case (START_DT_BOTH):
                setDate(calMinStartTime, year, month, day);
                setDate(calMaxStartTime, year, month, day);
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }
    }

    /**
     * Public method that allows setting the time for the iCalendarCode specified by iCalendarCode code.
     * @param iCalendarCode which iCalendarCode to modify, {CALENDAR_START_MIN | CALENDAR_START_MAX | CALENDAR_START_BOTH}
     * @param hour new value for hour {int from 0 to 23 inclusive}
     * @param minute new value for minute {int from 0 to 59 inclusive}
     */
    @Deprecated
    public void setTimeFor(int iCalendarCode, int hour, int minute) {
        String sMethodTag = "setTimeFor";
        
        switch (iCalendarCode) {
            case (START_DT_MIN):
                setTimeFor(calMinStartTime, hour, minute);
                break;
            case (START_DT_MAX):
                setTimeFor(calMaxStartTime, hour, minute);
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }
    }

    @Discouraged(message = "Use methods 'getYearFrom(int iCalendarCodeCode)' etc.")
    /**
     * Returns the specified iCalendarCode.
     * @param iCalendarCode which iCalendarCode to return, {CALENDAR_START_MIN | CALENDAR_START_MAX}
     * @return specified iCalendarCode, {dateTimeMin if iCalendarCode == CALENDAR_START_MIN | dateTimeMax if iCalendarCode == CALENDAR_START_MAX | null if iCalendarCode is something else}
     */
    @Deprecated
    public Calendar getCalendar(int iCalendarCode) {
        String sMethodTag = "getCalendar";
        Calendar outputCalendar = null;
        
        switch (iCalendarCode) {
            case (START_DT_MIN):
                outputCalendar = calMinStartTime;
                break;
            case (START_DT_MAX):
                outputCalendar = calMaxStartTime;
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }

        return outputCalendar;
    }

    /**
     * Public method for getting the YEAR field of a calendar specified by the calendar code.
     * @return year when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    @Deprecated
    public int getYearFrom(int iCalendarCode) {
        String sMethodTag = "getYearFrom";
        int year = -1;
        
        switch (iCalendarCode) {
            case (START_DT_MIN):
                year = calMinStartTime.get(Calendar.YEAR);
                break;
            case (START_DT_MAX):
                year = calMaxStartTime.get(Calendar.YEAR);
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }

        return year;
    }

    /**
     * Public method for getting the MONTH field of a calendar specified by the calendar code.
     * @return month when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    @Deprecated
    public int getMonthFrom(int iCalendarCode) {
        String sMethodTag = "getMonthFrom";
        int month = -1;

        switch (iCalendarCode) {
            case (START_DT_MIN):
                month = calMinStartTime.get(Calendar.MONTH) + 1;
                break;
            case (START_DT_MAX):
                month = calMaxStartTime.get(Calendar.MONTH) + 1;
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }

        return month;
    }

    /**
     * Public method for getting the DAY OF MONTH field of a calendar specified by the calendar code.
     * @return day when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    @Deprecated
    public int getDayFrom(int iCalendarCode) {
        String sMethodTag = "getDayFrom";
        int day = -1;

        switch (iCalendarCode) {
            case (START_DT_MIN):
                day = calMinStartTime.get(Calendar.DAY_OF_MONTH);
                break;
            case (START_DT_MAX):
                day = calMaxStartTime.get(Calendar.DAY_OF_MONTH);
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }

        return day;
    }

    /**
     * Public method for getting the HOUR OF DAY field of a calendar specified by the calendar code.
     * @return hour when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    @Deprecated
    public int getHourFrom(int iCalendarCode) {
        String sMethodTag = "getHourFrom";
        int hour = -1;

        switch (iCalendarCode) {
            case (START_DT_MIN):
                hour = calMinStartTime.get(Calendar.HOUR_OF_DAY);
                break;
            case (START_DT_MAX):
                hour = calMaxStartTime.get(Calendar.HOUR_OF_DAY);
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }

        return hour;
    }

    /**
     * Public method for getting the MINUTE field of a calendar specified by the calendar code.
     * @return minute when valid calendar code is give, otherwise return -1 to indicate an error.
     */
    @Deprecated
    public int getMinuteFrom(int iCalendarCode) {
        String sMethodTag = "getMinuteFrom";
        int minute = -1;

        switch (iCalendarCode) {
            case (START_DT_MIN):
                minute = calMinStartTime.get(Calendar.MINUTE);
                break;
            case (START_DT_MAX):
                minute = calMaxStartTime.get(Calendar.MINUTE);
                break;
            default:
                Log.e(sClassTag + sMethodTag, ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND + iCalendarCode);
                break;
        }

        return minute;
    }

    /**
     * Converts Calendar object's date & time information to a long integer.
     * @param iCalendarCode calendar code
     * @return calendar date & time info as long
     */
    @Deprecated
    private long getTime(int iCalendarCode) {
        Calendar dateTime = Calendar.getInstance();
        switch (iCalendarCode) {
            case (START_DT_MIN):
                dateTime = calMinStartTime;
                break;
            case (START_DT_MAX):
                dateTime = calMaxStartTime;
                break;
            default:
                System.err.println(ErrorMessages.ERR_FILTER_CALENDAR_NOT_FOUND);
                break;
        }
        return dateTime.getTime().getTime();
    }

    @Deprecated
    public void setLocationID(String sTheatreName) {
        iTheatreID = Database.getTheatre(sTheatreName).getID();
    }

//    /**
//     * Resets the date of starDateTimeMin to current date.
//     */
//    @Deprecated
//    private static  void clearStartDateMin() {
//        calMinStartTime.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
//        calMinStartTime.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
//        calMinStartTime.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//    }
//
//    /**
//     * Resets the date of startDateTimeMax to the maximum value, i.e., 31.12.3000
//     * 3000 is just an arbitrarily large number. I chose 3000 because I don't think anyone will
//     * be using this app in the year 3000 anymore :D
//     */
//    @Deprecated
//    private static void clearStartDateMax() {
//        calMaxStartTime.set(Calendar.YEAR, 3000);
//        calMaxStartTime.set(Calendar.MONTH, 11);
//        calMaxStartTime.set(Calendar.DAY_OF_MONTH, 31);
//    }
//
//    /**
//     * Resets the date of starDateTimeMin to current time.
//     */
//    @Deprecated
//    public static void clearStartTimeMin() {
//        calMinStartTime.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
//        calMinStartTime.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
//        calMinStartTime.set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND));
//    }
//
//    /**
//     * Resets the time of startDateTimeMax to the maximum value, i.e., 23:59:59
//     */
//    @Deprecated
//    public static void clearStartTimeMax() {
//        calMaxStartTime.set(Calendar.HOUR_OF_DAY, 23);
//        calMaxStartTime.set(Calendar.MINUTE, 59);
//        calMaxStartTime.set(Calendar.SECOND, 59);
//    }
//
//    /**
//     * Clears or resets the field specified by field code
//     * (aka resets the attribute value from the filter).
//     * @param fieldCode
//     */
//    @Deprecated
//    public void clearField(int fieldCode) {
//        switch (fieldCode) {
//            case (FIELD_TITLE):
//                clearTitle();
//                break;
//            case (FIELD_DATE):
//                clearStartDateMin();
//                clearStartDateMax();
//                break;
//            case (FIELD_TIME_MIN):
//                clearStartTimeMin();
//                break;
//            case (FIELD_TIME_MAX):
//                clearStartTimeMax();
//                break;
//            default:
//                System.err.println(ErrorMessages.ERR_FILTER_FIELD_NOT_FOUND + fieldCode);
//        }
//    }

    @NonNull
    @SuppressLint({"DefaultLocale", "DiscouragedApi"})
    @Override
    public String toString() {
        String s;
        s = "#### Filter.toString() ######################################\n" +
            "\t\tTheatre: %04d or '%s'\n" +
            "\t\tTitle: '%s'\n" +
            "\t\tMinimum Start Date: '%s' or %d\n" +
            "\t\tMinimum Start Time: '%s' or %d\n" +
            "\t\tMaximum Start Time: '%s' or %d\n" +
            "#############################################################";

        return String.format(
                s,
                instance.iTheatreID,
                Database.getTheatre(instance.iTheatreID).getName(),
                instance.sTitle,
                new Date(instance.lMinStartDate),
                new Date(instance.lMinStartDate).getTime(),
                new Date(instance.lMinStartTime),
                new Date(instance.lMinStartTime).getTime(),
                new Date(instance.lMaxStartTime),
                new Date(instance.lMaxStartTime).getTime()
        );
    }

    private boolean checkTitle(Show show) {
        boolean match = true;
        if (sTitle != null) {
            String sShowTitle = show.getTitle().toUpperCase();
            String sFilterTitle = sTitle.toUpperCase();
            if (!sShowTitle.contains(sFilterTitle)) {
                match = false;
            }
        }
        return match;
    }

    private boolean checkDate(Show show) {
        boolean match = true;

        // Check if filter's date field is set.


        long lCurrentDate = CalendarConverter.extractDateAsLong(Calendar.getInstance());
        long lFilterDate = CalendarConverter.extractDateAsLong(calMinStartTime);
        long lShowDate = CalendarConverter.extractDateAsLong(show.getStartDateTimeAsCalendar());

        // Check if date field is set

        if (lShowDate != lFilterDate) {
            match = false;
        }

        return match;
    }

    private boolean checkTime(Show show) {
        boolean match = true;
        long lFilterTimeMin = CalendarConverter.extractTimeAsLong(calMinStartTime);
        long lFilterTimeMax = CalendarConverter.extractTimeAsLong(calMaxStartTime);
        long lShowStartTime = CalendarConverter.extractTimeAsLong(show.getStartDateTimeAsCalendar());

        if ((lShowStartTime < lFilterTimeMin) || (lShowStartTime > lFilterTimeMax)) {
            match = false;
        }

        return match;
    }

    /**
     * Goes through all shows,
     * checks which shows match all search criteria,
     * adds matching shows to filtered list (ArrayList),
     * and returns the filtered list.
     * @return ArrayList containing shows (objects) that match all search criteria
     */
    public ArrayList<Show> getShows() {
        Log.i("Filter.getShows", "Started filtration process.");
        ArrayList<Show> allShows;
        ArrayList<Show> filteredShows = new ArrayList<>();

        /* Check if location ID is specified.
         * if true, search from the given location's list
         * if false, search from all shows
         */
        if (iTheatreID != ANY_LOCATION) {
            allShows = Database.getShowsAt(iTheatreID);
        } else {
            allShows = Database.getShowsAt(ANY_LOCATION);
        }

        Log.i("Filter.getShows", "Found " + allShows.size() + " shows for theatre " + iTheatreID);
        Log.i("Filter.getShows", "Starting comparing shows to search criteria.");
        Log.d("Filter.getShows", "Show ID; titleMatches; dateMatches; timeMatches");

        // Check other search criteria
//        for (Show show : allShows) {
//            boolean titleMatches = checkTitle(show);
//            boolean dateMatches = checkDate(show);
//            boolean timeMatches = checkTime(show);
//            Log.d("Filter.getShows", show.getShowID() + ";" + titleMatches + ";" + dateMatches + ";" + timeMatches);
//
//            if (titleMatches && dateMatches && timeMatches) {
//                filteredShows.add(show);
//                Log.d("Filter.getShows", "Added show " + show.getShowID() + " to matching shows.");
//            }
//        }

        Log.i("Filter.getShows", "Filtration process finished.");
        Log.i("Filter.getShows",filteredShows.size() + "/" + allShows.size() + " shows match the search criteria.");
        return allShows;
    }
}


