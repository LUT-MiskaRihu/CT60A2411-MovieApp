package com.example.movieapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private String sTitle;
    private String sOriginalTitle;
    private int iLength; // in minutes
    private int iProductionYear;
    private long lLocalRelease;
    private String sRating;
    private String sGenres;
    private String sSpokenLanguage;
    private ArrayList<String> alContentDescriptors;

    public Movie() {
        sTitle = null;
        sOriginalTitle = null;
        iLength = 0;
        iProductionYear = 0;
        lLocalRelease = 0;
        sRating = null;
        sGenres = null;
        sSpokenLanguage = null;
        alContentDescriptors = new ArrayList<>();
    }

    public Movie(
            String sTitle,
            String sOriginalTitle,
            int iLength,
            int iProductionYear,
            long lLocalRelease,
            String sRating,
            String sGenres,
            String sSpokenLanguage,
            ArrayList<String> alContentDescriptors)
    {
        this.sTitle = sTitle;
        this.sOriginalTitle = sOriginalTitle;
        this.iLength = iLength;
        this.iProductionYear = iProductionYear;
        this.lLocalRelease = lLocalRelease;
        this.sRating = sRating;
        this.sGenres = sGenres;
        this.sSpokenLanguage = sSpokenLanguage;
        this.alContentDescriptors = alContentDescriptors;
    }

    // Title
    public void setTitle(String sTitle) {
        this.sTitle = sTitle;
    }
    public String getTitle() {
        return sTitle;
    }

    // Original Title
    public void setOriginalTitle(String sOriginalTitle) {
        this.sOriginalTitle = sOriginalTitle;
    }
    public String getOriginalTitle() {
        return sOriginalTitle;
    }

    // Length
    public void setLength(int iLength) {
        this.iLength = iLength;
    }
    public void setLength(String sLength) {
        this.iLength = Integer.parseInt(sLength);
    }
    public int getLength() {
        return iLength;
    }

    // Production Year
    public void setProductionYear(int iProductionYear) {
        this.iProductionYear = iProductionYear;
    }
    public void setProductionYear(String sProductionYear) {
        this.iProductionYear = Integer.parseInt(sProductionYear);
    }
    public int getProductionYear() {
        return iProductionYear;
    }

    // Local Release
    public void setLocalReleaseDateTime(String sDateTime) {
        this.lLocalRelease = Parser.parseDateTime(sDateTime).getTime();
    }
    public long getLocalReleaseDateTime() {
        return lLocalRelease;
    }

    // (Age) Rating
    public void setRating(String sRating) {
        this.sRating = sRating;
    }
    public String getRating() {
        return sRating;
    }

    // Genres
    public void setGenres(String sGenres) {
        this.sGenres = sGenres;
    }
    public String getGenres() {
        return sGenres;
    }

    // Spoken Language
    public void setSpokenLanguage(String sSpokenLanguage) {
        this.sSpokenLanguage = sSpokenLanguage;
    }
    public String getSpokenLanguage() {
        return sSpokenLanguage;
    }

    // Content Descriptors

}
