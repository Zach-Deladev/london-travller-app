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

import java.util.ArrayList;
import java.util.List;

public class SelectGuideFragment extends Fragment {

    private List<Guides> guides;

    public SelectGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and get the RecyclerView
        View view = inflater.inflate(R.layout.fragment_select_guide2, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.guide_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get the arguments before returning the view
        Bundle args = getArguments();
        if (args != null) {
            Tour selectedTour = (Tour) args.getSerializable("selectedTour");
            String chosenDate = args.getString("chosenDate");
            int chosenTicketNumber = args.getInt("chosenTicketNumber");
            double totalPrice = args.getDouble("totalPrice");

            fetchGuidesAndUpdateUI(recyclerView, chosenDate, chosenTicketNumber, totalPrice);
        }

        return view;
    }

    private void fetchGuidesAndUpdateUI(RecyclerView recyclerView, String chosenDate, int chosenTicketNumber, double totalPrice) {
        // TODO: Fetch the guides from the database
        GuidesDao guidesDao = GuidesDaoProvider.getInstance(getContext());
        guides = guidesDao.getAllGuides();

        // Then create an instance of GuideAdapter
        // Initialize your GuideAdapter
        GuideAdapter adapter = new GuideAdapter(getContext(), guides, chosenDate, chosenTicketNumber, totalPrice);


        recyclerView.setAdapter(adapter);
    }






    @Override
    public void onStart() {
        super.onStart();
        fetchGuidesAndUpdateUI();
    }

    private void fetchGuidesAndUpdateUI() {
        // TODO: Fetch the guides from the database and update the RecyclerView
    }
}
