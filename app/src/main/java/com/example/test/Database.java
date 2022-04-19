package com.example.test;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Theatre> theatres = null;
    private static ArrayList<Show> shows = null;
    private static ArrayList<Show> filteredShows = null;
    private static Database instance = null;

    public Database() {
        System.out.println("#### Database.Database() ####################################");
        if (theatres == null) {
            System.out.println("\t\tHAETAAN TEATTEREITA");
            theatres = XMLReader.getInstance().getTheatres();
        }
        if (shows == null) {
            System.out.println("\t\tHAETAAN ELOKUVIA");
            shows = XMLReader.getInstance().getShows();
        }
        System.out.println("#############################################################");
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ArrayList<Theatre> getTheatres() {
        System.out.println("#### Database.getTheatres() #################################");
        for (Theatre t : theatres) {
            System.out.println("\t\t" + t.toString());
        }
        System.out.println("#############################################################");
        return theatres;
    }

    public ArrayList<Show> getShows() {
        System.out.println("#### Database.getShows() ####################################");
        for (Show s : shows) {
            System.out.println("\t\t" + s.toString());
        }
        System.out.println("#############################################################");
        return shows;
    }
}