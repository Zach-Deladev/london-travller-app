package com.example.loginsignupsystem.model;

import android.content.Context;

public class UsersDaoProvider {

    private static UsersDao instance;

    public static UsersDao getInstance(Context context) {
        if (instance == null) {
            DbHelper dbHelper = new DbHelper(context);
            instance = new UsersDaoImp(dbHelper);
        }
        return instance;
    }
}
