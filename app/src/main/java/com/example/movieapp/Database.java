package com.example.movieapp;

import android.util.Log;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Database {
    private static ArrayList<Theatre> alTheatres = null;
    private static ArrayList<Show> alAllShows = null;
    private static Database instance = null;

    private Database() {
        Log.i("Database.Database", "Started creating a new database.");
        loadTheatreInformation();
        loadShowInformation();
        Log.i("Database.Database","Finished creating a new database.");
    }
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private static void loadTheatreInformation() {
        // Get theatres and IDs
        if (alTheatres == null) {
            Log.i("Database.getTheatreInformation","Started fetching theatres/areas.");
            alTheatres = XMLReader.getOnlineTheatres();
        }
        for (Theatre t : alTheatres) {
            System.out.println("\t\t" + t.toString());
        }
        Log.i("Database.getTheatreInformation","Finished fetching theatres/areas. Found " + alTheatres.size() + " theatres/areas.");
    }

    private static void loadShowInformation() {
        // Get shows for each theater
        alAllShows = new ArrayList<>();

        for (Theatre theatre : alTheatres.subList(1, alTheatres.size() - 1)) {
            int iTheatreID = theatre.getID();
            Log.i("Database.getShowInformation","Started fetching shows for theatre " + iTheatreID + ".");

            if (theatre.getShows().size() == 0) {
                Log.i("Database.getShowInformation","Downloading data...");
                theatre.addShows(XMLReader.getOnlineShowsAt(iTheatreID));
                ArrayList<Show> alShows = theatre.getShows();

                if (alShows.size() != 0) {
                    alAllShows.addAll(alShows); // add each theatre's shows to the list containing all shows.
                    Log.i("Database.getShowInformation","Finished fetching shows for theatre " + iTheatreID + ". Found " + theatre.getShows().size() + " shows.");
                }
            }
        }

        sortAllShowsByStarTime();
    }

    private static void sortAllShowsByStarTime() {
        Collections.sort(alAllShows, new Comparator<Show>() {
            @Override
            public int compare(Show show1, Show show2) {
                return Long.compare(show1.getStartDateTime().getTime(), show2.getStartDateTime().getTime());
            }
        });
    }

    public static ArrayList<Theatre> getTheatres() {
        for (Theatre t : alTheatres) {
            Log.d("Database.getTheatres",t.toString());
        }
        Log.i("Database.getTheatres","Returned ArrayList, " + alTheatres.size() + " theatre(s).");
        return alTheatres;
    }

    @NonNull
    public static ArrayList<Show> getShowsAt(int iTheatreID) {
        ArrayList<Show> alShows;

        if (iTheatreID == Filter.ANY_LOCATION) {
            alShows = alAllShows;
        } else {
            alShows = getTheatre(iTheatreID).getShows();
        }

        Log.i("Database.getShowsAtTheatres","Returned ArrayList, " + alShows.size() + " show(s).");
        return alShows;
    }

    public static Theatre getTheatre(int id) {
        Theatre theatre = null;
        for (Theatre t : alTheatres) {
            if (t.getID() == id) {
                theatre = t;
            }
        }
        return theatre;
    }

    public static Theatre getTheatre(String name) {
        Theatre theatre = null;
        for (Theatre t : alTheatres) {
            if (t.getName().equals(name)) {
                theatre = t;
            }
        }
        return theatre;
    }
}
