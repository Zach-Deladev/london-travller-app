package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button;
TextView textView;
ImageView museums, landmarks, art, shopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Add image slider
        ImageSlider imageSlider = findViewById(R.id.imageSlider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.landscapeone, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.landscapetwo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.landscape3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        button = findViewById(R.id.button);
        // Initialize usersDao
        UsersDao usersDao = UsersDaoProvider.getInstance(MainActivity.this);

        //Find your ImageView components in the layout
        museums = findViewById(R.id.museums);
        landmarks = findViewById(R.id.landmarks);
        art = findViewById(R.id.art);
        shopping = findViewById(R.id.shopping);

        // Add onClick listeners to the images
        museums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTourListActivity("museum");
            }
        });

        landmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTourListActivity("landmarks");
            }
        });

        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTourListActivity("art");
            }
        });

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTourListActivity("shopping");
            }
        });

        // Find your TextView component in the layout
        textView = findViewById(R.id.textView);

        // Retrieve the username from the Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        if (username != null && !username.isEmpty()) {
            // Capitalize the first character of the username
            username = Character.toUpperCase(username.charAt(0)) + username.substring(1);

            // Format the string to say "Hello, <username>!" and set it in the TextView
            textView.setText(String.format("Hello, %s!", username));
        }

        // Log out user when logout button clicked
        Users loggedInUser = usersDao.getLoggedInUser();

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    usersDao.logOutUser(loggedInUser.getId());
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.putExtra("registrationSuccessMessage", "Logged out Successfully!");
                    startActivity(i);

                }
            });
        }

    private void startTourListActivity(String category) {
        Intent intent = new Intent(MainActivity.this, TourListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}



