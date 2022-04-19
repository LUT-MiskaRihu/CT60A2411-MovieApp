package com.example.test;

public class Theatre {
    private int id;
    private String name;

    public Theatre() {
        id = 0;
        name = null;
    }

    public Theatre(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return this.id + " - " + this.name;
    }
}
