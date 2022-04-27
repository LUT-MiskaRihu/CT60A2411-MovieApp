package com.example.movieapp;

import android.util.Log;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Theatre> theatres = null;
    private static ArrayList<Show> shows = null;
    private static Database instance = null;

    private Database() {
        Log.i("Database.Database", "Started creating a new database.");
        getTheatreInformation();
        getShowInformation();

        // Store all shows for future
        // these will be saved to a local database in the future (json, xml, or csv)
        shows = getTheatre("Valitse alue/teatteri").getShows();
        Log.i("Database.Database","Finished creating a new database.");
    }
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private static void getTheatreInformation() {
        // Get theatres and IDs
        if (theatres == null) {
            Log.i("Database.getTheatreInformation","Started fetching theatres/areas.");
            theatres = XMLReader.getOnlineTheatres();
        }
        for (Theatre t : theatres) {
            System.out.println("\t\t" + t.toString());
        }
        Log.i("Database.getTheatreInformation","Finished fetching theatres/areas. Found " + theatres.size() + " theatres/areas.");
    }

    private static void getShowInformation() {
        // Get movies for each theater
        for (Theatre theatre : theatres) {
            int iTheatreID = theatre.getID();
            Log.i("Database.getShowInformation","Started fetching shows for theatre " + iTheatreID + ".");
            if (theatre.getShows().size() == 0) {
                Log.i("Database.getShowInformation","Downloading data...");
                theatre.addShows(XMLReader.getOnlineShowsAt(iTheatreID));
                if (theatre.getShows() != null) {
                    Log.i("Database.getShowInformation","Finished fetching shows for theatre " + iTheatreID + ". Found " + theatre.getShows().size() + " shows.");
                }
            }
        }
    }

    public static ArrayList<Theatre> getTheatres() {
        for (Theatre t : theatres) {
            Log.d("Database.getTheatres",t.toString());
        }
        Log.i("Database.getTheatres","Returned ArrayList, " + theatres.size() + " theatre(s).");
        return theatres;
    }

    public static ArrayList<Show> getShowsAt(int iTheatreID) {
        ArrayList<Show> alShows = getTheatre(iTheatreID).getShows();
        for (Show show : alShows) {
            Log.d("Database.getShowsAt",show.toString());
        }
        Log.i("Database.getShowsAtTheatres","Returned ArrayList, " + alShows.size() + " show(s).");
        return alShows;
    }

    public static Theatre getTheatre(int id) {
        Theatre theatre = null;
        for (Theatre t : theatres) {
            if (t.getID() == id) {
                theatre = t;
            }
        }
        return theatre;
    }

    public static Theatre getTheatre(String name) {
        Theatre theatre = null;
        for (Theatre t : theatres) {
            if (t.getName().equals(name)) {
                theatre = t;
            }
        }
        return theatre;
    }
}
