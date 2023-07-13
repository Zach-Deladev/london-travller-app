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
    public static final int dbVersion = 8; // Increment database version due to schema changes

    // Database Name
    public static final String dbName = "LoginSignupSystem";

    // User Table Name
    public static final String dbUserTable = "users";

    // Tour Table Name
    public static final String dbTourTable = "tours";

    // Booking Table Name

    public static final String dbBookingTable = "bookings";

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
            + "date TEXT DEFAULT (datetime('now','localtime')), "
            + "tour TEXT); ";


    // The constructor for this class.
    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    private void insertPredefinedTours(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        // Tour 1
        cv.put("title", "Bond Street");
        cv.put("subtitle", "London's famous Bond Street is revered throughout the world for its wealth of elegant stores, exclusive brands, designer fashion, luxury goods, fine jewels, art and antiques.");
        cv.put("description", "Bond Street in the West End of London links Piccadilly in the south to Oxford Street in the north. Since the 18th century the street has housed many prestigious and upmarket fashion retailers. The southern section is Old Bond Street and the longer northern section New Bond Street—a distinction not generally made in everyday usage.\n" +
                "\n" +
                "The street was built on fields surrounding Clarendon House on Piccadilly, which were developed by Sir Thomas Bond. It was built up in the 1720s, and by the end of the 18th century was a popular place for the upper-class residents of Mayfair to socialise. Prestigious or expensive shops were established along the street, but it declined as a centre of social activity in the 19th century, although it held its reputation as a fashionable place for retail, and is home to the auction houses Sotheby's and Bonhams (formerly Phillips) and the department store Fenwick and jeweller Tiffany's. It is one of the most expensive and sought after strips of real estate in Europe.");
        cv.put("category", "shopping");
        cv.put("imageResource", R.drawable.bondstreet);
        db.insert(dbTourTable, null, cv);

        // Tour 2
        cv.put("title", "National Gallery");
        cv.put("subtitle", "The National Gallery is an art museum in Trafalgar Square in the City of Westminster, in Central London, England. Founded in 1824, in Trafalgar Square since 1838, it houses a collection of over 2,300 paintings dating from the mid-13th century to 1900. ");
        cv.put("description", "The National Gallery is an art museum in Trafalgar Square in the City of Westminster, in Central London, England. Founded in 1824, in Trafalgar Square since 1838, it houses a collection of over 2,300 paintings dating from the mid-13th century to 1900.[note 1] The current Director of the National Gallery is Gabriele Finaldi.\n" +
                "\n" +
                "The National Gallery is an exempt charity, and a non-departmental public body of the Department for Digital, Culture, Media and Sport.[3] Its collection belongs to the government on behalf of the British public, and entry to the main collection is free of charge.\n" +
                "\n" +
                "Unlike comparable museums in continental Europe, the National Gallery was not formed by nationalising an existing royal or princely art collection. It came into being when the British government bought 38 paintings from the heirs of John Julius Angerstein in 1824. After that initial purchase, the Gallery was shaped mainly by its early directors, especially Charles Lock Eastlake, and by private donations, which now account for two-thirds of the collection.[4] The collection is smaller than many European national galleries, but encyclopaedic in scope; most major developments in Western painting \"from Giotto to Cézanne\"[5] are represented with important works. It used to be claimed that this was one of the few national galleries that had all its works on permanent exhibition,[6] but this is no longer the case.\n" +
                "\n" +
                "The present building, the third site to house the National Gallery, was designed by William Wilkins. Building took from 1832 to 1838, when it opened. Only the facade onto Trafalgar Square remains essentially unchanged from this time, as the building has been expanded piecemeal throughout its history. Wilkins's building was often criticised for the perceived weaknesses of its design and for its lack of space; the latter problem led to the establishment of the Tate Gallery for British art in 1897. The Sainsbury Wing, a 1991 extension to the west by Robert Venturi and Denise Scott Brown, is a significant example of Postmodernist architecture in Britain.");
        cv.put("category", "museums");
        cv.put("imageResource", R.drawable.nationalgallery);
        db.insert(dbTourTable, null, cv);

        // Tour 2
        cv.put("title", "Tate Modern");
        cv.put("subtitle", "Tate Modern is an art gallery located in London. It houses the United Kingdom's national collection of international modern and contemporary art, and forms part of the Tate group together with Tate Britain, Tate Liverpool and Tate St Ives.[2] It is located in the former Bankside Power Station, in the Bankside area of the London Borough of Southwark.");
        cv.put("description", "Tate Modern is an art gallery located in London. It houses the United Kingdom's national collection of international modern and contemporary art, and forms part of the Tate group together with Tate Britain, Tate Liverpool and Tate St Ives.[2] It is located in the former Bankside Power Station, in the Bankside area of the London Borough of Southwark.\n" +
                "\n" +
                "Tate Modern is one of the largest museums of modern and contemporary art in the world. As with the UK's other national galleries and museums, there is no admission charge for access to the collection displays, which take up the majority of the gallery space, whereas tickets must be purchased for the major temporary exhibitions.\n" +
                "\n" +
                "Due to the COVID-19 pandemic the museum was closed for 173 days in 2020, and attendance plunged by 77 per cent to 1,432,991. However, it recovered strongly in 2022, with 3,883,160 visitors, making it the third most visited in Britain and the fourth-most visited art museum in the world.[3]\n" +
                "\n" +
                "The nearest railway and London Underground station is Blackfriars, which is 550 yards (0.5 km) from the gallery.[4]");
        cv.put("category", "art");
        cv.put("imageResource", R.drawable.tategallery);
        db.insert(dbTourTable, null, cv);

        // Tour 2
        cv.put("title", "Tower Bridge");
        cv.put("subtitle", "Tower Bridge is a Grade I listed combined bascule and suspension bridge in London, built between 1886 and 1894, designed by Horace Jones and engineered by John Wolfe Barry with the help of Henry Marc Brunel. ");
        cv.put("description", "Tower Bridge is a Grade I listed combined bascule and suspension bridge in London, built between 1886 and 1894, designed by Horace Jones and engineered by John Wolfe Barry with the help of Henry Marc Brunel.[1] It crosses the River Thames close to the Tower of London and is one of five London bridges owned and maintained by the Bridge House Estates, a charitable trust founded in 1282. The bridge was constructed to give better access to the East End of London, which had expanded its commercial potential in the 19th century. The bridge was opened by Edward, Prince of Wales and Alexandra, Princess of Wales in 1894.\n" +
                "\n" +
                "The bridge is 800 feet (240 m) in length and consists of two 213-foot (65 m) bridge towers connected at the upper level by two horizontal walkways, and a central pair of bascules that can open to allow shipping. Originally hydraulically powered, the operating mechanism was converted to an electro-hydraulic system in 1972. The bridge is part of the London Inner Ring Road and thus the boundary of the London congestion charge zone, and remains an important traffic route with 40,000 crossings every day. The bridge deck is freely accessible to both vehicles and pedestrians, whereas the bridge's twin towers, high-level walkways, and Victorian engine rooms form part of the Tower Bridge Exhibition.\n" +
                "\n" +
                "Tower Bridge has become a recognisable London landmark. It is sometimes confused with London Bridge, about 0.5 miles (800 m) upstream, which has led to a persistent urban legend about an American purchasing the wrong bridge.");
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
            // Recreate the tables
            onCreate(db);
        }
        Log.d("Database Operations", "Tables upgraded");
    }
}
