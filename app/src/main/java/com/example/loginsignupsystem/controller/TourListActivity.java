package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Tour;
import com.example.loginsignupsystem.model.TourDao;
import com.example.loginsignupsystem.model.TourDaoProvider;
import com.example.loginsignupsystem.model.TourListAdapter;

import java.util.List;

public class TourListActivity extends AppCompatActivity {

    private ListView listView; // ListView to display the tours
    private TourDao tourDao; // DAO to interact with the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);

        tourDao = TourDaoProvider.getInstance(this); // Get instance of TourDao

        // Find the ListView by its ID
        listView = findViewById(R.id.tourListView);

        // Get all tours from the database
        List<Tour> tours = tourDao.getAllTours();

        // Create a custom adapter to display each tour in the ListView
        TourListAdapter adapter = new TourListAdapter(this, tours);

        // Set the adapter on the ListView
        listView.setAdapter(adapter);
    }
}
