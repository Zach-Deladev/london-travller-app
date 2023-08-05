package com.example.loginsignupsystem.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoProvider;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle; // Define ActionBarDrawerToggle for burger menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Disable the default title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Inflate the custom view
        View customView = LayoutInflater.from(this).inflate(R.layout.toolbar_custom_logo, null);

        // Set the custom view
        toolbar.addView(customView);




        // Create the ActionBarDrawerToggle for the burger menu
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // Set the color of the burger menu icon
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    // Handle home navigation here
                } else if (itemId == R.id.nav_bookins) {
                    // Handle bookings navigation here
                } else if (itemId == R.id.nav_logout) {
                    // Handle logout navigation here
                    UsersDao usersDao = UsersDaoProvider.getInstance(MainActivity.this);
                    Users loggedInUser = usersDao.getLoggedInUser();
                    usersDao.logOutUser(loggedInUser.getId());
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.putExtra("registrationSuccessMessage", "Logged out Successfully!");
                    startActivity(i);
                }

                drawerLayout.closeDrawers(); // Close the drawer after selecting an item
                return true;
            }
        });

        // If this is the first creation of MainActivity, open HomeFragment
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, homeFragment);
            transaction.commit();
        }
    }
}
