package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.mindrot.jbcrypt.BCrypt;

public class UsersDaoImp implements UsersDao {

    // Constants for table and column names
    private static final String dbTable = "users";
    private static final String ID = "id";
    private static final String FULLNAME = "fullname";
    private static final String EMAILADDRESS = "emailaddress";
    private static final String PHONENUMBER = "phonenumber";
    private static final String PASSWORD = "password";
    private static final String ISLOGGEDIN = "isLoggedIn";

    // Database helper object for managing SQLite operations
    private final DbHelper dbHelper;

    // Constructor to initialize dbHelper
    public UsersDaoImp(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Method to hash password using BCrypt
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Method to check if an email exists in the database
    @Override
    public boolean emailExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbTable, new String[]{EMAILADDRESS}, EMAILADDRESS + "=?", new String[]{email}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Method to check if a phone number exists in the database
    @Override
    public boolean phoneExists(String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbTable, new String[]{PHONENUMBER}, PHONENUMBER + "=?", new String[]{phone}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Method to register a new user in the database
    @Override
    public long register(Users user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FULLNAME, user.getFullname());
        values.put(EMAILADDRESS, user.getEmailAddress());
        values.put(PHONENUMBER, user.getPhoneNumber());
        values.put(PASSWORD, hashPassword(user.getPassword()));

        long result = db.insert(dbTable, null, values);
        if (result == -1) {
            Log.d("Database Operations", "Failed to add user: " + user.getEmailAddress());
        } else {
            Log.d("Database Operations", "Successfully added user: " + user.getEmailAddress() + " with ID: " + result);
        }
        return result;
    }

    // Method to log in a user by updating the isLoggedIn column
    @Override
    public void logInUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ISLOGGEDIN, 1);
        db.update(dbTable, values, ID + "=?", new String[]{String.valueOf(userId)});
    }

    // Method to log out a user by updating the isLoggedIn column
    @Override
    public void logOutUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ISLOGGEDIN, 0);
        db.update(dbTable, values, ID + "=?", new String[]{String.valueOf(userId)});
    }

    // Method to retrieve the logged-in user's information
    @Override
    public Users getLoggedInUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbTable, null, ISLOGGEDIN + "=?", new String[]{"1"}, null, null, null);

        if (cursor.moveToFirst()) {
            Users user = new Users(
                    cursor.getString(cursor.getColumnIndex(FULLNAME)),
                    cursor.getString(cursor.getColumnIndex(EMAILADDRESS)),
                    cursor.getString(cursor.getColumnIndex(PHONENUMBER)),
                    cursor.getString(cursor.getColumnIndex(PASSWORD)));
            user.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    // Method to check if a user is logged in
    @Override
    public boolean isLoggedIn() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbTable, new String[]{ISLOGGEDIN}, ISLOGGEDIN + "=?", new String[]{"1"}, null, null, null);

        boolean isLoggedIn = cursor.moveToFirst();
        cursor.close();
        return isLoggedIn;
    }

    // Method to retrieve a user's information by user ID
    @Override
    public Users getUserById(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbTable, new String[]{ID, FULLNAME, EMAILADDRESS, PHONENUMBER, PASSWORD}, ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Users user = new Users(
                    cursor.getString(cursor.getColumnIndexOrThrow(FULLNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(EMAILADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PHONENUMBER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD))
            );
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
            cursor.close();
            return user;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    // Method to check user credentials during login and return user ID if successful
    @Override
    public int checkUserCredentials(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbTable, new String[]{ID, PASSWORD}, EMAILADDRESS + "=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String hashedPassword = cursor.getString(cursor.getColumnIndex(PASSWORD));
            int userId = cursor.getInt(cursor.getColumnIndex(ID));
            cursor.close();
            if (BCrypt.checkpw(password, hashedPassword)) {
                return userId;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
