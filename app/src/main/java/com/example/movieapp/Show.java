package com.example.movieapp;

import static java.lang.System.exit;

import androidx.annotation.NonNull;

import java.util.Date;

public class Show extends Movie {
    ////////////////////////////////////////////////////////////////////////////////
    // Calendar codes //////////////////////////////////////////////////////////////

    public static final int START_DATE_TIME = 0;
    public static final int END_DATE_TIME = 1;
    public static final int LOCAL_RELEASE_DATE_TIME = 2;

    ////////////////////////////////////////////////////////////////////////////////
    // Attributes //////////////////////////////////////////////////////////////////

    private int iShowID;
    private int iEventID;
    private Date dateStartDateTime;
    private Date dateEndDateTime;
    private int iTheatreID;
    private String sAuditoriumName;
    private String sPresentationMethod;

    ////////////////////////////////////////////////////////////////////////////////
    // Constructor(s) //////////////////////////////////////////////////////////////

    public Show() {
        this.iShowID = 0;
        this.iEventID = 0;
        this.dateStartDateTime = CalendarConverter.getEmptyDate();
        this.dateEndDateTime = CalendarConverter.getEmptyDate();
        this.iTheatreID = 0;
        this.sAuditoriumName = null;
        this.sPresentationMethod = null;
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Setters ang getters /////////////////////////////////////////////////////////

    // Show ID
    public void setShowID(int iShowID) {
        this.iShowID = iShowID;
    }
    public void setShowID(String sShowID) {
        this.iShowID = Integer.parseInt(sShowID);
    }
    public int getShowID() {
        return iShowID;
    }

    // Event ID
    public void setEventID(int iEventID) {
        this.iEventID = iEventID;
    }
    public void setEventID(String sEventID) {
        this.iEventID = Integer.parseInt(sEventID);
    }
    public int getEventID() {
        return iEventID;
    }

    // Start Time
    public void setStartDateTime(Date dateStartDateTime) {
        this.dateStartDateTime = dateStartDateTime;
    }

    public void setStartDateTime(String sStartDateTime) {
        this.dateStartDateTime = Parser.parseDateTime(sStartDateTime);
    }

    public Date getStartDateTime() {
        return this.dateStartDateTime;
    }

    // End Time
    public void setEndDateTime(Date dateEndDateTime) {
        this.dateEndDateTime = dateEndDateTime;
    }

    public void setEndDateTime(String sEndDateTime) {
        this.dateEndDateTime = Parser.parseDateTime(sEndDateTime);
    }

    public Date getEndDateTime() {
        return this.dateEndDateTime;
    }

    // Theatre ID
    public void setTheatreID(int iTheatreID) {
        this.iTheatreID = iTheatreID;
    }

    public void setTheatreID(String sTheatreID) {
        this.iTheatreID = Integer.parseInt(sTheatreID);
    }

    public int getTheatreID() {
        return this.iTheatreID;
    }

    // Auditorium Name
    public void setAuditoriumName(String sAuditoriumName) {
        this.sAuditoriumName = sAuditoriumName;
    }

    public String getAuditoriumName() {
        return this.sAuditoriumName;
    }

    // Presentation Method
    public void setPresentationMethod(String sPresentationMethod) {
        this.sPresentationMethod = sPresentationMethod;
    }

    public String getPresentationMethod() {
        return this.sPresentationMethod;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public String getDateString(int calendar) {
        String sDate = null;

        switch (calendar) {
            case(START_DATE_TIME):
                sDate = CalendarConverter.convertToDateString(dateStartDateTime);
                break;
            case(END_DATE_TIME):
                sDate = CalendarConverter.convertToDateString(dateEndDateTime);
                break;
            default:
                System.err.println(ErrorMessages.ERR_XML_READER_INVALID_CALENDAR_CODE);
                exit(ExitCodes.EXIT_INVALID_CALENDAR_CODE);
                break;
        }

        return sDate;
    }

    public String getTimeString(int calendar) {
        String sTime = null;

        switch (calendar) {
            case(START_DATE_TIME):
                //Log.v("test", "start time of " + iShowID);
                sTime = CalendarConverter.convertToTimeString(dateStartDateTime);
                break;
            case(END_DATE_TIME):
                //Log.v("test", "end time of " + iShowID);
                sTime = CalendarConverter.convertToTimeString(dateEndDateTime);
                break;
            default:
                System.err.println(ErrorMessages.ERR_XML_READER_INVALID_CALENDAR_CODE);
                exit(ExitCodes.EXIT_INVALID_CALENDAR_CODE);
                break;
        }

        return sTime;
    }

    @NonNull
    @Override
    public String toString() {
        String movie;

        movie = "\n" +
                getShowID() + "\n" + // debug item
                getTitle().toUpperCase() + " (" + getRating() + ")\n" +
                "\n" +
                getGenres() + "\n" +
                "\n" +
                "\n" +
                Database.getTheatre(iTheatreID).getName() + "\n" +
                getDateString(START_DATE_TIME) + " klo " + getTimeString(START_DATE_TIME) + " - " + getTimeString(END_DATE_TIME) + "\n";

        return movie;
    }
}
