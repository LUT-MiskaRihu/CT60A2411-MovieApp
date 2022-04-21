package com.example.movieapp;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Theatre> theatres = null;
    private static ArrayList<Show> shows = null;
    private static Database instance = null;
    private XMLReader xmlReader = XMLReader.getInstance();

    public Database() {
        System.out.println("#### Database.Database() ############################################");
        getTheatreInformation();
        System.out.println("---------------------------------------------------------------------");
        getShowInformation();

        // Store all shows for future
        // these will be saved to a local database in the future (json, xml, or csv)
        shows = getTheatre("Valitse alue/teatteri").getShows();
        System.out.println("#####################################################################");
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
            theatres = xmlReader.getTheatres();
        }
        for (Theatre t : theatres) {
            System.out.println("\t\t" + t.toString());
        }
    }

    private void getShowInformation() {
        // Get movies for each theater
        for (Theatre t : theatres) {
            int id = t.getID();
            System.out.println("\t\tFetching shows for theatre " + id);
            if (t.getShows().size() == 0) {
                System.out.println("\t\tDownloading data...");
                t.addShows(xmlReader.getShowsAt(id));
                if (t.getShows() != null) {
                    System.out.print("\t\tFound " + t.getShows().size() + " shows for theatre " + id);
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
