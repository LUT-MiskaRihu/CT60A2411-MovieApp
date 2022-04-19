package com.example.test;

import static java.lang.System.exit;
import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Show {
    // Calendar codes
    public static final int START_DATE_TIME = 0;
    public static final int END_DATE_TIME = 1;
    public static final int LOCAL_RELEASE = 2;

    // Exit codes
    public static final int EXIT_INVALID_CALENDAR_CODE = 1;

    // Error messages
    public static final String ERR_INVALID_CALENDAR_CODE = "Invalid calendar code: ";

    // Attributes
    private int id;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private int eventID;
    private String title;
    private String originalTitle;
    private int productionYear;
    private int length; // in minutes
    private Calendar localReleaseDateTime;
    private String rating;
    private String eventType;
    private String genres;
    private int locationID;
    private String PresentationMethodAndLanguage;

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public Show() {
        this.id = 0;
        this.startDateTime = Calendar.getInstance();
        this.endDateTime = Calendar.getInstance();
        this.eventID = 0;
        this.title = null;
        this.originalTitle = null;
        this.productionYear = 0;
        this.length = 0;
        this.localReleaseDateTime = Calendar.getInstance();
        this.rating = null;
        this.eventType = null;
        this.genres = null;
        this.locationID = 0;
        this.PresentationMethodAndLanguage = null;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setID(String id) {
        this.id = Integer.parseInt(id);
    }

    public Calendar getDateTime(int calendar) {
        Calendar dateTime = null;
        switch (calendar) {
            case(START_DATE_TIME):
                dateTime = startDateTime;
                break;
            case(END_DATE_TIME):
                dateTime = endDateTime;
                break;
            case(LOCAL_RELEASE):
                dateTime = localReleaseDateTime;
                break;
            default:
                System.err.println(ERR_INVALID_CALENDAR_CODE);
                exit(EXIT_INVALID_CALENDAR_CODE);
                break;
        }
        return dateTime;
    }

    public void setDateTime(int calendar, String dateTimeString) {
        Calendar dateTime = null;
        try {
            switch (calendar) {
                case(START_DATE_TIME):
                    startDateTime.setTime(dateTimeFormatter.parse(dateTimeString));
                    break;
                case(END_DATE_TIME):
                    endDateTime.setTime(dateTimeFormatter.parse(dateTimeString));
                    break;
                case(LOCAL_RELEASE):
                    localReleaseDateTime.setTime(dateTimeFormatter.parse(dateTimeString));
                    break;
                default:
                    System.err.println(ERR_INVALID_CALENDAR_CODE);
                    exit(EXIT_INVALID_CALENDAR_CODE);
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("DefaultLocale")
    public String getDateString(int calendar) {
        String date = "%02d.%02d.%04d";
        int day = -1;
        int month = -1;
        int year = -1;
        try {
            switch (calendar) {
                case(START_DATE_TIME):
                    day = startDateTime.get(Calendar.DAY_OF_MONTH);
                    month = startDateTime.get(Calendar.MONTH) + 1;
                    year = startDateTime.get(Calendar.YEAR);
                    break;
                case(END_DATE_TIME):
                    day = endDateTime.get(Calendar.DAY_OF_MONTH);
                    month = endDateTime.get(Calendar.MONTH) + 1;
                    year = endDateTime.get(Calendar.YEAR);
                    break;
                case(LOCAL_RELEASE):
                    day = localReleaseDateTime.get(Calendar.DAY_OF_MONTH);
                    month = localReleaseDateTime.get(Calendar.MONTH) + 1;
                    year = localReleaseDateTime.get(Calendar.YEAR);
                    break;
                default:
                    System.err.println(ERR_INVALID_CALENDAR_CODE);
                    exit(EXIT_INVALID_CALENDAR_CODE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format(date, day, month, year);
    }

    @SuppressLint("DefaultLocale")
    public String getTimeString(int calendar) {
        String time = "%02d:%02d";
        int hour = -1;
        int minute = -1;
        try {
            switch (calendar) {
                case(START_DATE_TIME):
                    hour = startDateTime.get(Calendar.HOUR_OF_DAY);
                    minute = startDateTime.get(Calendar.MINUTE) + 1;
                    break;
                case(END_DATE_TIME):
                    hour = endDateTime.get(Calendar.HOUR_OF_DAY);
                    minute = endDateTime.get(Calendar.MINUTE) + 1;
                    break;
                case(LOCAL_RELEASE):
                    hour = localReleaseDateTime.get(Calendar.HOUR_OF_DAY);
                    minute = localReleaseDateTime.get(Calendar.MINUTE) + 1;
                    break;
                default:
                    System.err.println(ERR_INVALID_CALENDAR_CODE);
                    exit(EXIT_INVALID_CALENDAR_CODE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format(time, hour, minute);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = Integer.parseInt(eventID);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = Integer.parseInt(productionYear);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLength(String length) {
        this.length = Integer.parseInt(length);
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = Integer.parseInt(locationID);
    }

    public String getPresentationMethodAndLanguage() {
        return PresentationMethodAndLanguage;
    }

    public void setPresentationMethodAndLanguage(String presentationMethodAndLanguage) {
        PresentationMethodAndLanguage = presentationMethodAndLanguage;
    }

    @NonNull
    @Override
    public String toString() {
        String movie;
        movie = getTitle() + '\n'
                + "Teatteri: " + getLocationID() + '\n'
                + "Päivämäärä: " + getDateString(START_DATE_TIME) + '\n'
                + "Alkaa: " + getTimeString(START_DATE_TIME) + '\n'
                + "Loppuu: " + getTimeString(END_DATE_TIME) + '\n'
                + "Kesto: " + getLength() + " minuuttia";
        return movie;
    }
}