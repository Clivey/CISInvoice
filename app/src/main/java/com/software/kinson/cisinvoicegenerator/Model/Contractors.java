package com.software.kinson.cisinvoicegenerator.Model;

public class Contractors {
    int id;
    String name, town;

    public Contractors(int id, String name, String town) {
        this.id = id;
        this.name = name;
        this.town = town;
    }

    public Contractors(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
