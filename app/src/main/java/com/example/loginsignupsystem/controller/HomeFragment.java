package com.example.loginsignupsystem.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoProvider;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private ImageView museums, landmarks, art, shopping;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize image slider
        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.nationalgallery, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.bondstreet, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.tategallery, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);



        // Initialize usersDao
        UsersDao usersDao = UsersDaoProvider.getInstance(getActivity());

        //Find ImageView components in the layout
        museums = view.findViewById(R.id.museums);
        landmarks = view.findViewById(R.id.landmarks);
        art = view.findViewById(R.id.art);
        shopping = view.findViewById(R.id.shopping);

        // Add onClick listeners to the images
        museums.setOnClickListener(v -> startTourListFragment("museums"));
        landmarks.setOnClickListener(v -> startTourListFragment("landmarks"));
        art.setOnClickListener(v -> startTourListFragment("art"));
        shopping.setOnClickListener(v -> startTourListFragment("shopping"));







        return view;
    }

    private void startTourListFragment(String category) {
        TourListFragment tourListFragment = new TourListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        tourListFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, tourListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
