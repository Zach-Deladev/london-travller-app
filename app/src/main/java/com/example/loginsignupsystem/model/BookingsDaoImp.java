package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookingsDaoImp implements BookingsDao{
    private static final String dbTable = "bookings";
    private static final String ID = "id";
    private static final String USERID = "userid";
    private static final String REFERENCE = "reference";
    private static final String DATE = "date";
    private static final String TOUR = "tour";
    private static final String PRICE = "price"; // added PRICE field

    private final DbHelper dbHelper;

    public BookingsDaoImp(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long addBooking(Bookings booking) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERID, booking.getUserId());
        cv.put(REFERENCE, booking.getReference());
        cv.put(DATE, booking.getDate());
        cv.put(TOUR, booking.getTour());
        cv.put(PRICE, booking.getPrice());

        long result = db.insert(dbTable, null, cv);
        db.close();
        return result;
    }


    public List<Bookings> getAllBookings() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Bookings> bookings = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Bookings booking = new Bookings(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getInt(cursor.getColumnIndex(USERID)),
                        cursor.getInt(cursor.getColumnIndex(REFERENCE)),
                        cursor.getString(cursor.getColumnIndex(DATE)),
                        cursor.getString(cursor.getColumnIndex(TOUR)),
                        cursor.getDouble(cursor.getColumnIndex(PRICE)) // added PRICE field
                );
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookings;
    }

    public Bookings getBookingById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Bookings booking = null;
        Cursor cursor = db.query(dbTable, null, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            booking = new Bookings(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getInt(cursor.getColumnIndex(USERID)),
                    cursor.getInt(cursor.getColumnIndex(REFERENCE)),
                    cursor.getString(cursor.getColumnIndex(DATE)),
                    cursor.getString(cursor.getColumnIndex(TOUR)),
                    cursor.getDouble(cursor.getColumnIndex(PRICE)) // added PRICE field
            );
        }
        cursor.close();
        db.close();
        return booking;
    }

    public List<Bookings> getBookingsByUserId(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Bookings> bookings = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, USERID + "=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Bookings booking = new Bookings(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getInt(cursor.getColumnIndex(USERID)),
                        cursor.getInt(cursor.getColumnIndex(REFERENCE)),
                        cursor.getString(cursor.getColumnIndex(DATE)),
                        cursor.getString(cursor.getColumnIndex(TOUR)),
                        cursor.getDouble(cursor.getColumnIndex(PRICE)) // added PRICE field
                );
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookings;
    }

}
