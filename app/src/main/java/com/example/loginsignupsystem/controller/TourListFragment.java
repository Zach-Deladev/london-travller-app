package com.example.loginsignupsystem.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Tour;
import com.example.loginsignupsystem.model.TourDao;
import com.example.loginsignupsystem.model.TourDaoProvider;
import com.example.loginsignupsystem.model.TourListAdapter;

import java.util.List;

public class TourListFragment extends Fragment {
    private ListView listView;
    private TourDao tourDao;
    private String category;

    public TourListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tour_list, container, false);
        // Create instance of tour dao provider to interact with the tour dao interface
        tourDao = TourDaoProvider.getInstance(getContext());
        // Define Listview
        listView = view.findViewById(R.id.tourListView);
        // Define list and set it to get tours function and give it the argument of the category we sent from homepage
        List<Tour> tours = tourDao.getToursByCategory(category);
        // Define tour list adapter and pass it the argument of the tours object
        TourListAdapter adapter = new TourListAdapter(getActivity(), tours);
        // set the list view adapter to the adapter above
        listView.setAdapter(adapter);

        return view;
    }
}
