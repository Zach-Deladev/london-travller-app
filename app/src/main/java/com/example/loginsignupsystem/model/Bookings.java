package com.example.loginsignupsystem.model;

public class Bookings {

    private int id;
    private int userId;
    private int reference;
    private String date;
    private String tour;
    private double price;
    private int tickets; // Number of tickets field

    // Constructor for creating new Booking
    public Bookings(int userId, int reference, String date, String tour, double price, int tickets) {
        this.userId = userId;
        this.reference = reference;
        this.date = date;
        this.tour = tour;
        this.price = price;
        this.tickets = tickets; // set tickets
    }

    // Constructor for creating Booking from database values
    public Bookings(int id, int userId, int reference, String date, String tour, double price, int tickets) {
        this.id = id;
        this.userId = userId;
        this.reference = reference;
        this.date = date;
        this.tour = tour;
        this.price = price;
        this.tickets = tickets; // set tickets
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
}
