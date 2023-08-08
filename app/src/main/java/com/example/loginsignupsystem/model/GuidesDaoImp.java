package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GuidesDaoImp implements GuidesDao {
    private static final String dbTable = "guides"; // Table name
    private static final String ID = "id"; // Column name for ID
    private static final String GUIDNAME = "guideName"; // Column name for guide name
    private static final String LANGUAGE = "language"; // Column name for language
    private static final String IMAGE = "image"; // Column name for image

    private final DbHelper dbHelper; // Database helper object

    // Constructor
    public GuidesDaoImp(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Method to add a guide to the database
    public long addGuide(Guides guide) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Get writable database
        ContentValues cv = new ContentValues();
        cv.put(GUIDNAME, guide.getGuidName());
        cv.put(LANGUAGE, guide.getLanguage());
        cv.put(IMAGE, guide.getImage());

        long result = db.insert(dbTable, null, cv); // Insert the guide
        db.close();
        return result; // Return the result of the insertion
    }

    // Method to retrieve all guides from the database
    public List<Guides> getAllGuides() {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        List<Guides> guides = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Create a guide object for each record
                Guides guide = new Guides(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(GUIDNAME)),
                        cursor.getString(cursor.getColumnIndex(LANGUAGE)),
                        cursor.getInt(cursor.getColumnIndex(IMAGE))
                );
                guides.add(guide); // Add the guide to the list
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return guides; // Return the list of guides
    }

    // Method to retrieve a guide by its ID
    public Guides getGuideById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        Guides guide = null;
        Cursor cursor = db.query(dbTable, null, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            // Create a guide object if the record exists
            guide = new Guides(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(GUIDNAME)),
                    cursor.getString(cursor.getColumnIndex(LANGUAGE)),
                    cursor.getInt(cursor.getColumnIndex(IMAGE))
            );
        }
        cursor.close();
        db.close();
        return guide; // Return the guide object
    }

    // Method to retrieve guides by language
    public List<Guides> getGuidesByLanguage(String language) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        List<Guides> guides = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, LANGUAGE + "=?", new String[]{language}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Create a guide object for each record matching the language
                Guides guide = new Guides(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(GUIDNAME)),
                        cursor.getString(cursor.getColumnIndex(LANGUAGE)),
                        cursor.getInt(cursor.getColumnIndex(IMAGE))
                );
                guides.add(guide); // Add the guide to the list
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return guides; // Return the list of guides by language
    }
}
