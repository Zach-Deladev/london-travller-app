package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookingsDaoImp implements BookingsDao {
    // Define table name and column names
    private static final String dbTable = "bookings";
    private static final String ID = "id";
    private static final String USERID = "userid";
    private static final String REFERENCE = "reference";
    private static final String DATE = "date";
    private static final String TOUR = "tour";
    private static final String PRICE = "price";
    private static final String TICKETS = "tickets"; // New TICKETS field

    // Database helper instance
    private final DbHelper dbHelper;

    public BookingsDaoImp(DbHelper dbHelper) {
        this.dbHelper = dbHelper; // Initialize DbHelper
    }

    // Method to add a new booking
    public long addBooking(Bookings booking) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Get writable database
        ContentValues cv = new ContentValues(); // Create content values to hold data
        cv.put(USERID, booking.getUserId());
        cv.put(REFERENCE, booking.getReference());
        cv.put(DATE, booking.getDate());
        cv.put(TOUR, booking.getTour());
        cv.put(PRICE, booking.getPrice());
        cv.put(TICKETS, booking.getTickets()); // Add TICKETS field

        long result = db.insert(dbTable, null, cv); // Insert the data
        db.close(); // Close the database connection
        return result; // Return the result
    }

    // Method to retrieve all bookings
    public List<Bookings> getAllBookings() {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        List<Bookings> bookings = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, null, null, null, null, null);

        if (cursor.moveToFirst()) { // Move to the first row
            do {
                bookings.add(createBookingFromCursor(cursor)); // Add booking from cursor
            } while (cursor.moveToNext());
        }
        cursor.close(); // Close the cursor
        db.close(); // Close the database connection
        return bookings; // Return the bookings list
    }

    // Method to retrieve a booking by ID
    public Bookings getBookingById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Bookings booking = null;
        Cursor cursor = db.query(dbTable, null, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) { // Move to the first row
            booking = createBookingFromCursor(cursor); // Create booking from cursor
        }
        cursor.close(); // Close the cursor
        db.close(); // Close the database connection
        return booking; // Return the booking
    }

    // Method to retrieve bookings by user ID
    public List<Bookings> getBookingsByUserId(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        List<Bookings> bookings = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, USERID + "=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor.moveToFirst()) { // Move to the first row
            do {
                bookings.add(createBookingFromCursor(cursor)); // Add booking from cursor
            } while (cursor.moveToNext());
        }
        cursor.close(); // Close the cursor
        db.close(); // Close the database connection
        return bookings; // Return the bookings list
    }

    // Helper method to create a Booking object from the cursor
    private Bookings createBookingFromCursor(Cursor cursor) {
        return new Bookings(
                cursor.getInt(cursor.getColumnIndex(ID)),
                cursor.getInt(cursor.getColumnIndex(USERID)),
                cursor.getInt(cursor.getColumnIndex(REFERENCE)),
                cursor.getString(cursor.getColumnIndex(DATE)),
                cursor.getString(cursor.getColumnIndex(TOUR)),
                cursor.getDouble(cursor.getColumnIndex(PRICE)),
                cursor.getInt(cursor.getColumnIndex(TICKETS)) // Retrieve TICKETS field
        );
    }
}
