package com.example.movieapp;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Theatre> theatres = null;
    private static ArrayList<Show> shows = null;
    private static Database instance = null;

    public Database() {
        System.out.println("\t\tDatabase");
        getTheatreInformation();
        getShowInformation();
        // Store all shows for future
        // these will be saved to a local database in the future (json, xml, or csv)
        shows = getTheatre("Valitse alue/teatteri").getShows();
    }

    
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void getTheatreInformation() {
        // Get theatres and IDs
        if (theatres == null) {
            System.out.println("\t\tFetching theatre info...");
            theatres = XMLReader.getInstance().getTheatres();
        }
    }

    private void getShowInformation() {
        // Get movies for each theater
        for (Theatre t : theatres) {
            System.out.println("\t\tFetching shows for theatre " + t.getID());
            if (t.getShows() == null) {
                System.out.println("\t\tDownloading data...");
                t.addShows(XMLReader.getInstance().getShowsAt(t.getID()));
                if (t.getShows() != null) {
                    System.out.print("\t\tFound " + t.getShows().size() + " shows for theatre " + t.getID());
                }
            }
        }
    }

    public ArrayList<Theatre> getTheatres() {
        for (Theatre t : theatres) {
            System.out.println("\t\t" + t.toString());
        }
        return theatres;
    }

    public ArrayList<Show> getAllShows() {
        for (Show s : shows) {
            System.out.println("\t\t" + s.toString());
        }
        return shows;
    }

    public ArrayList<Show> getShowsAt(int id) {
        return getTheatre(id).getShows();
    }

    public Theatre getTheatre(int id) {
        Theatre theatre = null;
        for (Theatre t : theatres) {
            if (t.getID() == id) {
                theatre = t;
            }
        }
        return theatre;
    }

    public Theatre getTheatre(String name) {
        Theatre theatre = null;
        for (Theatre t : theatres) {
            if (t.getName().equals(name)) {
                theatre = t;
            }
        }
        return theatre;
    }
}
