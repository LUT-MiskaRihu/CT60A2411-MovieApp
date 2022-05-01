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
    // Attributes

    private int iTheatreID;      // stores the location id of a theater
    private String sTitle;

    private long lMinStartDate;
    private long lMinStartTime;
    private long lMaxStartTime;

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
        final String sMethodTag = "setTheatre";

        instance.iTheatreID = Database.getTheatre(sTheatreName).getID();
        Log.i(sClassTag + "." + sMethodTag, "Set iTheatreID to " + instance.iTheatreID + ".");
    }

    public void clearTheatreID() {
        final String sMethodTag = "clearTheatreID";

        instance.iTheatreID = NOT_SET;
        Log.i(sClassTag + "." + sMethodTag, "Set iTheatreID to " + instance.iTheatreID + ".");
    }

    public int getTheatreID() {
        final String sMethodTag = "getTheatreID";

        Log.d(sClassTag + "." + sMethodTag, "Returning iTheatreID with the value " + instance.iTheatreID + ".");
        return instance.iTheatreID;
    }

    // Title
    public void setTitle(String sTitle) {
        final String sMethodTag = "setTitle";

        instance.sTitle = sTitle;
        Log.i(sClassTag + "." + sMethodTag, "Set sTitle to " + instance.sTitle + ".");
    }

    public String getTitle() {
        return instance.sTitle;
    }

    public void clearTitle() {
        final String sMethodTag = "clearTitle";

        instance.sTitle = null;
        Log.i(sClassTag + "." + sMethodTag, "Set sTitle to " + instance.sTitle + ".");
    }

    // Date
    public void setMinStartDate(long lMinStartDate) {
        final String sMethodTag = "setMinStartDate(long)";

        instance.lMinStartDate = lMinStartDate;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartDate to " + instance.lMinStartDate + ".");
    }

    public void setMinStartDate(@NonNull Date dateMinStartDate) {
        final String sMethodTag = "setMinStartDate(Date)";

        instance.lMinStartDate = dateMinStartDate.getTime();
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartDate to " + instance.lMinStartDate + ".");
    }

    public void setMinStartDate(String sMinStartDate) {
        final String sMethodTag = "setMinStartDate(String)";

        setMinStartDate(Parser.parseDate(sMinStartDate));
    }

    public void clearMinStartDate() {
        final String sMethodTag = "clearMinStartDate";

        instance.lMinStartDate = NOT_SET;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartDate to " + instance.lMinStartDate + ".");
    }

    public long getMinStartDate() {
        final String sMethodTag = "getMinStartDate";

        Log.d(sClassTag + "." + sMethodTag, "Returning lMinStartDate with the value " + instance.lMinStartDate + ".");
        return instance.lMinStartDate;
    }

    // Min Time
    public void setMinStartTime(long lMinStartTime) {
        final String sMethodTag = "setMinStartTime(long)";

        instance.lMinStartTime = lMinStartTime;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartTime to " + instance.lMinStartTime + ".");
    }

    public void setMinStartTime(@NonNull Date dateMinStartTime) {
        final String sMethodTag = "setMinStartTime(Date)";

        instance.lMinStartTime = dateMinStartTime.getTime();
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartTime to " + instance.lMinStartTime + ".");
    }

    public void setMinStartTime(String sMinStartTime) {
        final String sMethodTag = "setMinStartTime(String)";

        setMinStartTime(Parser.parseTime(sMinStartTime));
    }

    public void clearMinStartTime() {
        final String sMethodTag = "clearMinStartTime";

        instance.lMaxStartTime = NOT_SET;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartTime to " + instance.lMinStartTime + ".");
    }

    public long getMinStartTime() {
        final String sMethodTag = "clearMinStartTime";

        Log.d(sClassTag + "." + sMethodTag, "Returning lMinStartTime with the value " + instance.lMinStartTime + ".");
        return instance.lMinStartTime;
    }

    // Max Time
    public void setMaxStartTime(long lMaxStartTime) {
        final String sMethodTag = "setMaxStartTime(long)";

        instance.lMaxStartTime = lMaxStartTime;
        Log.i(sClassTag + "." + sMethodTag, "Set lMaxStartTime to " + instance.lMaxStartTime + ".");
    }

    public void setMaxStartTime(@NonNull Date dateMaxStartTime) {
        final String sMethodTag = "setMaxStartTime(Date)";

        instance.lMaxStartTime = dateMaxStartTime.getTime();
        Log.i(sClassTag + "." + sMethodTag, "Set lMaxStartTime to " + instance.lMaxStartTime + ".");
    }

    public void setMaxStartTime(String sMaxStartTime) {
        final String sMethodTag = "setMaxStartTime(String)";

        setMaxStartTime(Parser.parseTime(sMaxStartTime));
    }

    public void clearMaxStartTime() {
        final String sMethodTag = "clearMaxStartTime";

        instance.lMaxStartTime = NOT_SET;
        Log.i(sClassTag + "." + sMethodTag, "Set lMaxStartTime to " + instance.lMaxStartTime + ".");
    }

    public long getMaxStartTime() {
        final String sMethodTag = "getMaxStartTime";

        Log.d(sClassTag + "." + sMethodTag, "Returning lMaxStartTime with the value " + instance.lMaxStartTime + ".");
        return instance.lMaxStartTime;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

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

    private boolean checkDate(@NonNull Show show) {
        boolean match = true;
        long lCurrentDate = CalendarConverter.extractDateAsLong(Calendar.getInstance());
        System.out.println("lCurrentDate:\t" + lCurrentDate);
        long lShowDate = CalendarConverter.extractDateAsLong(show.getStartDateTimeAsCalendar());
        System.out.println("lShowDate:\t" + lShowDate);
        long lFilterDate = instance.lMinStartDate;
        System.out.println("lFilterDate:\t" + lFilterDate);
        System.out.println("---------------------------------------------------------------------");

        if (lShowDate < lCurrentDate) {
            match = false;
        } else {
            if (lFilterDate != NOT_SET) {
                if (lShowDate != lFilterDate) {
                    match = false;
                }
            }
        }

//        long lCurrentDate = CalendarConverter.extractDateAsLong(Calendar.getInstance());
//        long lFilterDate = CalendarConverter.extractDateAsLong(calMinStartTime);
//        long lShowDate = CalendarConverter.extractDateAsLong(show.getStartDateTimeAsCalendar());
//
//        if (lShowDate != lFilterDate) {
//            match = false;
//        }

        return match;
    }

    private boolean checkTime(Show show) {
        boolean match = true;

//        long lFilterTimeMin = CalendarConverter.extractTimeAsLong(calMinStartTime);
//        long lFilterTimeMax = CalendarConverter.extractTimeAsLong(calMaxStartTime);
//        long lShowStartTime = CalendarConverter.extractTimeAsLong(show.getStartDateTimeAsCalendar());
//
//        if ((lShowStartTime < lFilterTimeMin) || (lShowStartTime > lFilterTimeMax)) {
//            match = false;
//        }

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
        ArrayList<Show> alAllShows;
        ArrayList<Show> alMatchingShows = new ArrayList<>();

        /* Check if location ID is specified.
         * if true, search from the given location's list
         * if false, search from all shows
         */
        if (iTheatreID != ANY_LOCATION) {
            alAllShows = Database.getShowsAt(iTheatreID);
        } else {
            alAllShows = Database.getShowsAt(ANY_LOCATION);
        }

        Log.i("Filter.getShows", "Found " + alAllShows.size() + " shows for theatre " + iTheatreID);
        Log.i("Filter.getShows", "Starting comparing shows to search criteria.");
        Log.d("Filter.getShows", "Show ID; titleMatches; dateMatches; timeMatches");

        // Check other search criteria
        for (Show show : alAllShows) {
            boolean titleMatches = checkTitle(show);
            boolean dateMatches = checkDate(show);
//            boolean timeMatches = checkTime(show);
//            Log.d("Filter.getShows", show.getShowID() + ";" + titleMatches + ";" + dateMatches + ";" + timeMatches);
//
            if (titleMatches && dateMatches /*&& timeMatches*/) {
                alMatchingShows.add(show);
                Log.d("Filter.getShows", "Added show " + show.getShowID() + " to matching shows.");
            }
        }

        Log.i("Filter.getShows", "Filtration process finished.");
        Log.i("Filter.getShows",alMatchingShows.size() + "/" + alAllShows.size() + " shows match the search criteria.");
        return alMatchingShows;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // toString()

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
}


