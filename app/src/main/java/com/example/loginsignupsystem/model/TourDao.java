package com.example.loginsignupsystem.model;

import java.util.List;

public interface TourDao {
    // This method is used to add a tour to the database
    long addTour(Tour tour);

    // This method is used to get all tours from the database
    List<Tour> getAllTours();

    // Get tours by category
    List<Tour> getToursByCategory(String category);

    // This method is used to get a tour by its id
    Tour getTourById(int id);

    //... any other operations you need
}

