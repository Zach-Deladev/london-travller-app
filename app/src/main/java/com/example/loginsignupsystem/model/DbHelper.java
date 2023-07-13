package com.example.loginsignupsystem.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    public static final int dbVersion = 7; // Increment database version due to schema changes

    // Database Name
    public static final String dbName = "LoginSignupSystem";

    // User Table Name
    public static final String dbUserTable = "users";

    // Tour Table Name
    public static final String dbTourTable = "tours";

    // SQL statement to create users table
    public static final String CREATE_USERS_TABLE = "CREATE TABLE " + dbUserTable + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "fullname TEXT, "
            + "emailaddress TEXT, "
            + "phonenumber TEXT, "
            + "password TEXT, "
            + "isloggedin INTEGER DEFAULT 0);";

    // SQL statement to create tours table
    public static final String CREATE_TOURS_TABLE = "CREATE TABLE " + dbTourTable + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "title TEXT, "
            + "subtitle TEXT, "
            + "description TEXT, "
            + "category TEXT, "
            + "imageResource INTEGER);"; // Assuming this column holds drawable resource IDs

    // The constructor for this class.
    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the user table
        db.execSQL(CREATE_USERS_TABLE);
        Log.d("Database Operations", "Table " + dbUserTable + " created");

        // Create the tour table
        db.execSQL(CREATE_TOURS_TABLE);
        Log.d("Database Operations", "Table " + dbTourTable + " created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // If the users table already exists, delete it
            db.execSQL("DROP TABLE IF EXISTS " + dbUserTable);
            // If the tours table already exists, delete it
            db.execSQL("DROP TABLE IF EXISTS " + dbTourTable);
            // Recreate the tables
            onCreate(db);
        }
        Log.d("Database Operations", "Tables upgraded");
    }
}
