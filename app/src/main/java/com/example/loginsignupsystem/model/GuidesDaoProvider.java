package com.example.loginsignupsystem.model;

import android.content.Context;

public class GuidesDaoProvider {
    private static GuidesDao instance;

    public static GuidesDao getInstance(Context context) {
        if (instance == null) {
            DbHelper dbHelper = new DbHelper(context);
            instance = new GuidesDaoImp(dbHelper);
        }
        return instance;
    }
}
