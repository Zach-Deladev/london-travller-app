package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.DbHelper;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoImp;

public class SplashActivity extends AppCompatActivity {

    private UsersDao usersDao; // Data Access Object for Users

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Creating an instance of DbHelper and UsersDao for database operations
        DbHelper dbHelper = new DbHelper(this);
        usersDao = new UsersDaoImp(dbHelper);

        // Creating a handler to execute code after a delay of 2000ms (2 seconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if a user is already logged in
                if (usersDao.isLoggedIn()) {
                    // Redirect to the main activity if the user is logged in
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    // Redirect to the login activity if no user is logged in
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                // Close the splash activity to prevent the user from navigating back to it
                finish();
            }
        }, 2000); // Delay time in milliseconds
    }
}
