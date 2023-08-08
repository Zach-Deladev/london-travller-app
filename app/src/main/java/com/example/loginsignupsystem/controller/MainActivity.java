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
    private DrawerLayout drawerLayout; // Main layout containing the navigation drawer
    private ActionBarDrawerToggle toggle; // Toggle button for opening/closing the navigation drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DrawerLayout (container for navigation drawer)
        drawerLayout = findViewById(R.id.drawer_layout);

        // Initialize and set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Hide the default title

        // Inflate custom view (e.g., app logo) and add it to the toolbar
        View customView = LayoutInflater.from(this).inflate(R.layout.toolbar_custom_logo, null);
        toolbar.addView(customView);

        // Configure the ActionBarDrawerToggle for burger menu and its icon color
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize NavigationView and its item click listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle click event on navigation items
                int itemId = item.getItemId();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Determine the clicked item and replace the fragment accordingly
                if (itemId == R.id.nav_home) {
                    // Navigate to home
                    transaction.replace(R.id.fragment_container, new HomeFragment());
                } else if (itemId == R.id.nav_bookings) {
                    // Navigate to bookings list
                    transaction.replace(R.id.fragment_container, new BookingsListFragment());
                } else if (itemId == R.id.nav_contact) {
                    // Navigate to contact page
                    transaction.replace(R.id.fragment_container, new ContactFragment());
                } else if (itemId == R.id.nav_logout) {
                    // Handle logout: clear the logged-in user and navigate to login page
                    UsersDao usersDao = UsersDaoProvider.getInstance(MainActivity.this);
                    Users loggedInUser = usersDao.getLoggedInUser();
                    usersDao.logOutUser(loggedInUser.getId());
                    startActivity(new Intent(MainActivity.this, LoginActivity.class).putExtra("registrationSuccessMessage", "Logged out Successfully!"));
                    return true; // Return here to prevent the transaction from being committed
                }

                transaction.addToBackStack(null); // Add transaction to back stack (optional)
                transaction.commit();
                drawerLayout.closeDrawers(); // Close the navigation drawer
                return true;
            }
        });

        // If this is the first creation of MainActivity, set the HomeFragment as the default
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new HomeFragment());
            transaction.commit();
        }
    }
}
