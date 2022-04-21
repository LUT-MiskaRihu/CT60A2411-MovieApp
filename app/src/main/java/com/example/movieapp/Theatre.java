package com.example.movieapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Theatre {
    private int id;
    private String name;
    private ArrayList<Show> shows = new ArrayList<>();

    public Theatre() {
        id = 0;
        name = null;
    }

    public Theatre(int id, String name, ArrayList<Show> shows) {
        this.id = id;
        this.name = name;
        addShows(shows);
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Show addShow(Show show) {
        shows.add(show);
        return show;
    }

    public void addShows(@NonNull ArrayList<Show> shows) {
        for (int i=0; i<shows.size(); i++) {
            Show show = shows.get(i);
            if (!this.shows.contains(show)) {
                this.shows.add(show);
            }
        }
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
