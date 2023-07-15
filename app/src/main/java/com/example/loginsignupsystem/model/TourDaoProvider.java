package com.example.loginsignupsystem.model;

import android.content.Context;

public class TourDaoProvider {

    private static TourDao instance;

    public static TourDao getInstance(Context context) {
        if (instance == null) {
            DbHelper dbHelper = new DbHelper(context);
            instance = new TourDaoImp(dbHelper);
        }
        return instance;
    }
}