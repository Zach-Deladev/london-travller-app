package com.example.loginsignupsystem.model;

import android.content.Context;

import java.io.Serializable;

public class Tour implements Serializable {
    private int id;
    private String title;
    private String subTitle;
    private String description;
    private String category;
    private String imageResource;

    public Tour(int id, String title, String subTitle, String description, String category, String imageResource) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.category = category;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Your other getters and setters...

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imageResource, "drawable", context.getPackageName());
    }
}
