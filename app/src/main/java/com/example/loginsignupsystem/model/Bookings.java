package com.example.loginsignupsystem.model;

public class Bookings {

    private int id;
    private int userId;
    private int reference;
    private String date;
    private String tour;

    // Constructor for creating new Booking
    public Bookings(int userId, int reference, String tour) {
        this.userId = userId;
        this.reference = reference;
        this.tour = tour;
    }

    // Constructor for creating Booking from database values
    public Bookings(int id, int userId, int reference, String date, String tour) {
        this.id = id;
        this.userId = userId;
        this.reference = reference;
        this.date = date;
        this.tour = tour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }
}
