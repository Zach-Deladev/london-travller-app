package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.DbHelper;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoImp;

public class SplashActivity extends AppCompatActivity {

    private UsersDao usersDao;

   ImageView travelers_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        travelers_logo = (ImageView) findViewById(R.id.travelers_logo);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        travelers_logo.startAnimation(animation);

        animation.setFillAfter(true);
        animation.setDuration(3000);


        // Initialize the database helper and Users DAO.
        DbHelper dbHelper = new DbHelper(this);
        usersDao = new UsersDaoImp(dbHelper);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This is where we will check if the user is already logged in or not
                if (usersDao.isLoggedIn()) {
                    // If the user is already logged in, redirect to the main activity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    // If the user is not logged in, redirect to the login activity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                // We finish the SplashActivity so the user can't go back to it
                finish();
            }
        }, 2000);
    }
}
