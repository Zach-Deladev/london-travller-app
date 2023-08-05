package com.example.loginsignupsystem.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.GuideAdapter;
import com.example.loginsignupsystem.model.Guides;
import com.example.loginsignupsystem.model.GuidesDao;
import com.example.loginsignupsystem.model.GuidesDaoProvider;
import com.example.loginsignupsystem.model.Tour;

import java.util.List;

public class SelectGuideFragment extends Fragment {

    private List<Guides> guides;
    private RecyclerView recyclerView;
    private String chosenDate;
    private int chosenTicketNumber;
    private double totalPrice;
    private Tour selectedTour;

    public SelectGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and get the RecyclerView
        View view = inflater.inflate(R.layout.fragment_select_guide2, container, false);
        recyclerView = view.findViewById(R.id.guide_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get the arguments before returning the view
        Bundle args = getArguments();
        if (args != null) {
            selectedTour = (Tour) args.getSerializable("selectedTour");
            chosenDate = args.getString("chosenDate");
            chosenTicketNumber = args.getInt("chosenTicketNumber");
            totalPrice = args.getDouble("totalPrice");
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchGuidesAndUpdateUI();
    }

    private void fetchGuidesAndUpdateUI() {
        // Fetch the guides from the database
        GuidesDao guidesDao = GuidesDaoProvider.getInstance(getContext());
        guides = guidesDao.getAllGuides();

        // Initialize your GuideAdapter
        GuideAdapter adapter = new GuideAdapter(getContext(), guides, chosenDate, chosenTicketNumber, totalPrice, selectedTour);

        recyclerView.setAdapter(adapter);
    }
}
