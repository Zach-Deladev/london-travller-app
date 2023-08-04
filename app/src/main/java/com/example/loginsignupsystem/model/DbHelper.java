package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.loginsignupsystem.R;

public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    public static final int dbVersion = 20; // Increment database version due to schema changes

    // Database Name
    public static final String dbName = "LoginSignupSystem";

    // User Table Name
    public static final String dbUserTable = "users";

    // Tour Table Name
    public static final String dbTourTable = "tours";

    // Booking Table Name

    public static final String dbBookingTable = "bookings";

    // Guide table name

    public static  final String dbGuideTable = "guides";

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

    // SQL statement to create bookings table
    public static final String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + dbBookingTable + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "userid INTEGER, "
            + "reference INTEGER, "
            + "date TEXT , "
            + "tour TEXT); ";

    // SQL statement to create guide table
    public static final String CREATE_GUIDE_TABLE = "CREATE TABLE " + dbGuideTable + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "guideName TEXT, "
            + "language, TEXT,"
            + "image INTEGER); ";

    // The constructor for this class.
    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    // Insert guides
    private void insertGuides(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        //Guide one
        cv.put("guideName", "Marcos");
        cv.put("language", "Spanish");
        cv.put("image", R.drawable.marcos);
        db.insert(dbGuideTable, null, cv);

        cv.clear(); // clear the old data

        // Guide two
        cv.put("guideName", "Adam");
        cv.put("language", "English");
        cv.put("image", R.drawable.adam);
        db.insert(dbGuideTable, null, cv);

        cv.clear(); // clear the old data

        // Guide Three
        cv.put("guideName", "Wagner");
        cv.put("language", "German");
        cv.put("image", R.drawable.wagner);
        db.insert(dbGuideTable, null, cv);

        Log.d("Database Operations", "Predefined Guides inserted");
    }

    // Insert predefined tours method

    private void insertPredefinedTours(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        // Tour 1
        cv.put("title", "Bond Street");
        cv.put("subtitle", "London's famous Bond Street is revered throughout the world for its wealth of elegant stores, exclusive brands, designer fashion, luxury goods, fine jewels, art and antiques.");
        cv.put("description", "Bond Street in the West End of London links Piccadilly in the south to Oxford Street in the north. Since the 18th century the street has housed many prestigious and upmarket fashion retailers. \n" +
                "\n" +
                "The street was built on fields surrounding Clarendon House on Piccadilly, which were developed by Sir Thomas Bond. It was built up in the 1720s, and by the end of the 18th century was a popular place for the upper-class residents of Mayfair to socialise.");
        cv.put("category", "shopping");
        cv.put("imageResource", R.drawable.bondstreet);
        db.insert(dbTourTable, null, cv);

        // Tour 2
        cv.put("title", "National Gallery");
        cv.put("subtitle", "The National Gallery is an art museum in Trafalgar Square in the City of Westminster, in Central London, England. Founded in 1824, in Trafalgar Square since 1838, it houses a collection of over 2,300 paintings dating from the mid-13th century to 1900. ");
        cv.put("description", "The National Gallery is an art museum in Trafalgar Square in the City of Westminster, in Central London, England. Founded in 1824, in Trafalgar Square since 1838, it houses a collection of over 2,300 paintings dating from the mid-13th century to 1900.[note 1] The current Director of the National Gallery is Gabriele Finaldi.\n"

             );
        cv.put("category", "museums");
        cv.put("imageResource", R.drawable.nationalgallery);
        db.insert(dbTourTable, null, cv);

        // Tour 2
        cv.put("title", "Tate Modern");
        cv.put("subtitle", "Tate Modern is an art gallery located in London. It houses the United Kingdom's national collection of international modern and contemporary art, and forms part of the Tate group together with Tate Britain, Tate Liverpool and Tate St Ives.[2] It is located in the former Bankside Power Station, in the Bankside area of the London Borough of Southwark.");
        cv.put("description", "Tate Modern is an art gallery located in London. It houses the United Kingdom's national collection of international modern and contemporary art, and forms part of the Tate group together with Tate Britain, Tate Liverpool and Tate St Ives.[2] It is located in the former Bankside Power Station, in the Bankside area of the London Borough of Southwark.\n"
              );
        cv.put("category", "art");
        cv.put("imageResource", R.drawable.tategallery);
        db.insert(dbTourTable, null, cv);

        // Tour 2
        cv.put("title", "Tower Bridge");
        cv.put("subtitle", "Tower Bridge is a Grade I listed combined bascule and suspension bridge in London, built between 1886 and 1894, designed by Horace Jones and engineered by John Wolfe Barry with the help of Henry Marc Brunel. ");
        cv.put("description", "Tower Bridge is a Grade I listed combined bascule and suspension bridge in London, built between 1886 and 1894, designed by Horace Jones and engineered by John Wolfe Barry with the help of Henry Marc Brunel.[1] It crosses the River Thames close to the Tower of London and is one of five London bridges owned and maintained by the Bridge House Estates, a charitable trust founded in 1282. The bridge was constructed to give better access to the East End of London, which had expanded its commercial potential in the 19th century.");
        cv.put("category", "landmarks");
        cv.put("imageResource", R.drawable.towerbridge);
        db.insert(dbTourTable, null, cv);

        // Add more tours as needed...

        Log.d("Database Operations", "Predefined tours inserted");
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
        //Insert initial tours
        insertPredefinedTours(db);
        // Create the bookings table
        db.execSQL(CREATE_BOOKINGS_TABLE);
        Log.d("Database Operations", "Table " + dbBookingTable + " created");

        // Create guide table
        db.execSQL(CREATE_GUIDE_TABLE);
        insertGuides(db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // If the users table already exists, delete it
            db.execSQL("DROP TABLE IF EXISTS " + dbUserTable);
            // If the tours table already exists, delete it
            db.execSQL("DROP TABLE IF EXISTS " + dbTourTable);
            // If the bookings table already exists, delete it
            db.execSQL("DROP TABLE IF EXISTS " + dbBookingTable);

            db.execSQL("DROP TABLE IF EXISTS " + dbGuideTable);
            // Recreate the tables
            onCreate(db);
        }
        Log.d("Database Operations", "Tables upgraded");
    }
}
