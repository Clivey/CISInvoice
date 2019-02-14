package com.software.kinson.cisinvoicegenerator.Model;

public class Days {
    String date;
    int dayID, startTime, endTime, totalTime;

    public Days(int dayID, String date, int startTime, int endTime, int totalTime) {
        this.dayID = dayID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
    }


    //Empty constructor
    public Days(){}

    public int getDayID() { return dayID; }

    public void setDayID(int dayID) { this.dayID = dayID; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
