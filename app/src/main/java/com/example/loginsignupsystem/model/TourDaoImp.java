package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TourDaoImp implements TourDao {
    private static final String dbTable = "tours"; // Table name
    private static final String ID = "id"; // Column name for ID
    private static final String TITLE = "title"; // Column name for title
    private static final String SUBTITLE = "subtitle"; // Column name for subtitle
    private static final String DESCRIPTION = "description"; // Column name for description
    private static final String CATEGORY = "category"; // Column name for category
    private static final String IMAGERESOURCE = "imageResource"; // Column name for image resource

    private final DbHelper dbHelper; // Database helper object

    // Constructor
    public TourDaoImp(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Method to add a tour to the database
    @Override
    public long addTour(Tour tour) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Get writable database
        ContentValues cv = new ContentValues();
        cv.put(TITLE, tour.getTitle());
        cv.put(SUBTITLE, tour.getSubTitle());
        cv.put(DESCRIPTION, tour.getDescription());
        cv.put(CATEGORY, tour.getCategory());
        cv.put(IMAGERESOURCE, tour.getImageResource());

        long result = db.insert(dbTable, null, cv); // Insert the tour
        db.close();
        return result; // Return the result of the insertion
    }

    // Method to retrieve all tours from the database
    @Override
    public List<Tour> getAllTours() {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        List<Tour> tours = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Create a tour object for each record
                Tour tour = new Tour(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(TITLE)),
                        cursor.getString(cursor.getColumnIndex(SUBTITLE)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(IMAGERESOURCE))
                );
                tours.add(tour); // Add the tour to the list
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tours; // Return the list of tours
    }

    // Method to retrieve tours by category
    @Override
    public List<Tour> getToursByCategory(String category) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        List<Tour> tours = new ArrayList<>();

        // Query the database for tours with the given category
        Cursor cursor = db.query(dbTable, null, CATEGORY + "=?", new String[]{category}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Create a tour object for each record matching the category
                Tour tour = new Tour(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(TITLE)),
                        cursor.getString(cursor.getColumnIndex(SUBTITLE)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(IMAGERESOURCE))
                );
                tours.add(tour); // Add the tour to the list
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tours; // Return the list of tours by category
    }

    // Method to retrieve a tour by its ID
    @Override
    public Tour getTourById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        Tour tour = null;
        Cursor cursor = db.query(dbTable, null, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            // Create a tour object if the record exists
            tour = new Tour(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(TITLE)),
                    cursor.getString(cursor.getColumnIndex(SUBTITLE)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(IMAGERESOURCE))
            );
        }
        cursor.close();
        db.close();
        return tour; // Return the tour object
    }
}
