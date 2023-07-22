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
    private Button button;
    private TextView textView;
    private ImageView museums, landmarks, art, shopping;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize image slider
        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.landscapeone, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.landscapetwo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.landscape3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Initialize logout button
        button = view.findViewById(R.id.button);

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

        // Find TextView component in the layout
        textView = view.findViewById(R.id.textView);

        // Retrieve the username from the Intent
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("username");

        if (username != null && !username.isEmpty()) {
            // Capitalize the first character of the username
            username = Character.toUpperCase(username.charAt(0)) + username.substring(1);

            // Format the string to say "Hello, <username>!" and set it in the TextView
            textView.setText(String.format("Hello, %s!", username));
        }

        // Log out user when logout button clicked
        Users loggedInUser = usersDao.getLoggedInUser();
        button.setOnClickListener(v -> {
            usersDao.logOutUser(loggedInUser.getId());
            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.putExtra("registrationSuccessMessage", "Logged out Successfully!");
            startActivity(i);
        });

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
