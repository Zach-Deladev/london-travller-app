package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Tour;

public class MainActivity extends AppCompatActivity {
    // Define a BroadcastReceiver to handle tour selection broadcasts
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Check if the broadcast's action is "tourSelected"
            if (intent.getAction().equals("tourSelected")) {
                // If so, get the selected Tour from the intent's extras and open the TourInfoFragment
                Tour selectedTour = (Tour) intent.getSerializableExtra("selectedTour");
                openTourInfoFragment(selectedTour);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If this is the first creation of MainActivity, open HomeFragment
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, homeFragment);
            transaction.commit();
        }

        // Register the BroadcastReceiver to listen for "tourSelected" broadcasts
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("tourSelected"));
    }

    @Override
    protected void onDestroy() {
        // Unregister the BroadcastReceiver when the activity is destroyed to prevent memory leaks
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    // Method to open the TourInfoFragment with the selected Tour
    private void openTourInfoFragment(Tour selectedTour) {
        // Create a new TourInfoFragment
        TourInfoFragment tourInfoFragment = new TourInfoFragment();

        // Create a new Bundle to hold the arguments
        Bundle args = new Bundle();
        args.putSerializable("selectedTour", selectedTour);

        // Set the arguments for the fragment
        tourInfoFragment.setArguments(args);

        // Start a FragmentTransaction to replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace the current fragment with the new TourInfoFragment
        transaction.replace(R.id.fragment_container, tourInfoFragment);

        // Add the transaction to the back stack, allowing the user to navigate back to the previous fragment
        transaction.addToBackStack(null);

        // Commit the transaction, applying the changes
        transaction.commit();
    }
}
