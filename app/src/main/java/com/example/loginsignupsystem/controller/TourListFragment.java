package com.example.loginsignupsystem.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Tour;
import com.example.loginsignupsystem.model.TourDao;
import com.example.loginsignupsystem.model.TourDaoProvider;
import com.example.loginsignupsystem.model.TourListAdapter;

import java.util.List;
public class TourListFragment  extends Fragment{
    private  ListView listView;
    private TourDao tour;
    private  String category;

    public  TourListFragment(){
}


public void OnCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        if(getArguments()!=null){
            category= getArguments().getString("category");
        }
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // this inflates the layout of the tourlistfragment
        View view = inflater.inflate(R.layout.fragment_tour_list,container,false);
// This Creates an istance of tour dao provider to interact with the tour dao interface
        tour = TourDaoProvider.getInstance(getContext());
 //This is to define the listview
        listView = view.findViewById(R.id.tourListView);
//This defines the list and set it to give tour argument through tour function
        List<Tour>tours= tour.getToursByCategory(category);
// defines the tour list adapter and pass the argument of the tour object
        TourListAdapter adapter = new TourListAdapter(getActivity(),tours);
// sets the list adapter to the adapter above
        listView.setAdapter(adapter);
        return view;
    }
    }

