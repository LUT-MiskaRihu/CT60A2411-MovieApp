package com.example.movieapp;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;

public class Filter {
    private static final String sClassTag = "Filter";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Location Codes

    private static final int ANY_LOCATION = 1029; // 1029 refers to theater name "Valitse alue/teatteri" aka all possible locations.


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Other Codes

    public static final int NOT_SET = -1;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes

    private int iTheatreID;
    private String sTitle;

    private long lStartDateMin;
    private long lStartTimeMin;
    private long lStartTimeMax;

    private static Filter instance = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor and getInstance

    private Filter() {
        iTheatreID = NOT_SET;
        sTitle = null;
        lStartDateMin = NOT_SET;
        lStartTimeMin = NOT_SET;
        lStartTimeMax = NOT_SET;
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
        instance.lStartDateMin = lMinStartDate;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartDate to " + instance.lStartDateMin + ".");
    }

    public void setMinStartDate(@NonNull Date dateMinStartDate) {
        final String sMethodTag = "setMinStartDate(Date)";
        instance.lStartDateMin = dateMinStartDate.getTime();
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartDate to " + instance.lStartDateMin + ".");
    }

    public void setMinStartDate(String sMinStartDate) {
        setMinStartDate(Parser.parseDate(sMinStartDate));
    }

    public void clearMinStartDate() {
        final String sMethodTag = "clearMinStartDate";
        instance.lStartDateMin = NOT_SET;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartDate to " + instance.lStartDateMin + ".");
    }

    public long getMinStartDate() {
        final String sMethodTag = "getMinStartDate";
        Log.d(sClassTag + "." + sMethodTag, "Returning lMinStartDate with the value " + instance.lStartDateMin + ".");
        return instance.lStartDateMin;
    }

    // Min Time
    public void setMinStartTime(long lMinStartTime) {
        final String sMethodTag = "setMinStartTime(long)";
        instance.lStartTimeMin = lMinStartTime;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartTime to " + instance.lStartTimeMin + ".");
    }

    public void setMinStartTime(@NonNull Date dateMinStartTime) {
        final String sMethodTag = "setMinStartTime(Date)";
        instance.lStartTimeMin = dateMinStartTime.getTime();
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartTime to " + instance.lStartTimeMin + ".");
    }

    public void setMinStartTime(String sMinStartTime) {
        setMinStartTime(Parser.parseTime(sMinStartTime));
    }

    public void clearMinStartTime() {
        final String sMethodTag = "clearMinStartTime";

        instance.lStartTimeMax = NOT_SET;
        Log.i(sClassTag + "." + sMethodTag, "Set lMinStartTime to " + instance.lStartTimeMin + ".");
    }

    public long getMinStartTime() {
        final String sMethodTag = "clearMinStartTime";

        Log.d(sClassTag + "." + sMethodTag, "Returning lMinStartTime with the value " + instance.lStartTimeMin + ".");
        return instance.lStartTimeMin;
    }

    // Max Time
    public void setMaxStartTime(long lMaxStartTime) {
        final String sMethodTag = "setMaxStartTime(long)";
        instance.lStartTimeMax = lMaxStartTime;
        Log.i(sClassTag + "." + sMethodTag, "Set lMaxStartTime to " + instance.lStartTimeMax + ".");
    }

    public void setMaxStartTime(@NonNull Date dateMaxStartTime) {
        final String sMethodTag = "setMaxStartTime(Date)";
        instance.lStartTimeMax = dateMaxStartTime.getTime();
        Log.i(sClassTag + "." + sMethodTag, "Set lMaxStartTime to " + instance.lStartTimeMax + ".");
    }

    public void setMaxStartTime(String sMaxStartTime) {
        setMaxStartTime(Parser.parseTime(sMaxStartTime));
    }

    public void clearMaxStartTime() {
        final String sMethodTag = "clearMaxStartTime";
        instance.lStartTimeMax = NOT_SET;
        Log.i(sClassTag + "." + sMethodTag, "Set lMaxStartTime to " + instance.lStartTimeMax + ".");
    }

    public long getMaxStartTime() {
        final String sMethodTag = "getMaxStartTime";
        Log.d(sClassTag + "." + sMethodTag, "Returning lMaxStartTime with the value " + instance.lStartTimeMax + ".");
        return instance.lStartTimeMax;
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

    private boolean checkDateTime(@NonNull Show show, @NonNull Date dateCurrentDateTime) {
        boolean match = true;
        long lCurrentDateTime = dateCurrentDateTime.getTime();
        long lShowDateTime = show.getStartDateTime().getTime();

        if (lShowDateTime < lCurrentDateTime) {
            match = false;
        }

        return match;
    }

    private boolean checkDate(@NonNull Show show) {
        final String sMethodTag = "checkDate";
        int iShowID = show.getShowID();

        Log.i(sClassTag + "." + sMethodTag, "Checking date of show " + iShowID + ".");

        boolean match = true;
        long lShowDate = CalendarConverter.extractDateAsLong(show.getStartDateTime());
        long lFilterDate = instance.lStartDateMin;

        if (lFilterDate != NOT_SET) {
            if (lShowDate != lFilterDate) {
                Log.i(sClassTag + "." + sMethodTag, "Discarded show " + iShowID + ", date doesn't match selected date.");
                match = false;
            }
        }

        return match;
    }

    private boolean checkTime(@NonNull Show show) {
        final String sMethodTag = "checkTitle";

        boolean match = true;
        long lFilterTimeMin = instance.lStartTimeMin;
        long lFilterTimeMax = instance.lStartTimeMax;
        long lShowTime = CalendarConverter.extractTimeAsLong(show.getStartDateTime());

        // Remove shows that begin too early.
        if (lFilterTimeMin != NOT_SET) {
            if (lShowTime < lFilterTimeMin) {
                match = false;
            }
        }

        // Remove shows that begin too late.
        if (lFilterTimeMax != NOT_SET) {
            if (lShowTime > lFilterTimeMax) {
                match = false;
            }
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
        ArrayList<Show> alAllShows;
        ArrayList<Show> alMatchingShows = new ArrayList<>();
        Date dateCurrentDateTime = new Date();

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
            boolean bShowHasNotStarted = checkDateTime(show, dateCurrentDateTime); // remove shows that have already started or ended.
            boolean bTitleMatches = checkTitle(show);
            boolean bDateMatches = checkDate(show);
            boolean bTimeMatches = checkTime(show);
//            Log.d("Filter.getShows", show.getShowID() + ";" + titleMatches + ";" + dateMatches + ";" + timeMatches);

            if (bShowHasNotStarted && bTitleMatches && bDateMatches && bTimeMatches) {
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

    @Override
    @NonNull
    @SuppressLint({"DefaultLocale", "DiscouragedApi"})
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
                new Date(instance.lStartDateMin),
                new Date(instance.lStartDateMin).getTime(),
                new Date(instance.lStartTimeMin),
                new Date(instance.lStartTimeMin).getTime(),
                new Date(instance.lStartTimeMax),
                new Date(instance.lStartTimeMax).getTime()
        );
    }
}


