package com.example.loginsignupsystem.model;

import java.io.Serializable;

public class Guides implements Serializable {

    private int id;

    private String guidName;

    private  String language;

    private int image ;


    public Guides(int id, String guidName, String language, int image) {
        this.id = id;
        this.guidName = guidName;
        this.language = language;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuidName() {
        return guidName;
    }

    public void setGuidName(String guidName) {
        this.guidName = guidName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
