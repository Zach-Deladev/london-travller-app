package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TourDaoImp implements TourDao {
    private static final String dbTable = "tours";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String SUBTITLE = "subTitle";
    private static final String DESCRIPTION = "description";
    private static final String CATEGORY = "category";
    private static final String IMAGERESOURCE = "imageResource";

    private final DbHelper dbHelper;

    public TourDaoImp(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public long addTour(Tour tour) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, tour.getTitle());
        cv.put(SUBTITLE, tour.getSubTitle());
        cv.put(DESCRIPTION, tour.getDescription());
        cv.put(CATEGORY, tour.getCategory());
        cv.put(IMAGERESOURCE, tour.getImageResource());

        long result = db.insert(dbTable, null, cv);
        db.close();
        return result;
    }

    @Override
    public List<Tour> getAllTours() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Tour> tours = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tour tour = new Tour(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(TITLE)),
                        cursor.getString(cursor.getColumnIndex(SUBTITLE)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(IMAGERESOURCE))
                );
                tours.add(tour);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tours;
    }

    @Override
    public Tour getTourById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Tour tour = null;
        Cursor cursor = db.query(dbTable, null, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
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
        return tour;
    }
}
