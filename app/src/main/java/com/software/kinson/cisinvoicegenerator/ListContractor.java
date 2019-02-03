package com.software.kinson.cisinvoicegenerator;

public class ListContractor {
    private String ID;
    private String name;
    private String town;

    public ListContractor(String name, String ID,  String town) {
        this.ID = ID;
        this.name = name;
        this.town = town;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
