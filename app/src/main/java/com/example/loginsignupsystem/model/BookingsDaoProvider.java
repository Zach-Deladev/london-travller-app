package com.example.loginsignupsystem.model;

import android.content.Context;

public class BookingsDaoProvider {
    private static BookingsDao instance;

    public static BookingsDao getInstance(Context context) {
        if (instance == null) {
            DbHelper dbHelper = new DbHelper(context);
            instance = new BookingsDaoImp(dbHelper);
        }
        return instance;
    }
}
