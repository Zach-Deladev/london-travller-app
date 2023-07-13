package com.example.loginsignupsystem.model;

public class Users {
    private int id;
    private String fullname;
    private String emailAddress;
    private String phoneNumber;
    private String password;


    // Constructor
    public Users(String fullname, String emailAddress, String phoneNumber, String password) {

        this.fullname = fullname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;

    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
