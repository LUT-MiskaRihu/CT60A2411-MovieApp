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


