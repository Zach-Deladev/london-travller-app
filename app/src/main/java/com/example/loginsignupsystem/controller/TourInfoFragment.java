package com.example.loginsignupsystem.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Tour;

public class TourInfoFragment extends Fragment {
    private Tour selectedTour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_info, container, false);

        // Retrieve the selected tour from the passed arguments, if available
        if (getArguments() != null) {
            selectedTour = (Tour) getArguments().getSerializable("selectedTour");
        }

        // Retrieve the views to populate with tour data
        ImageView tourImage = view.findViewById(R.id.tour_image);
        TextView tourTitle = view.findViewById(R.id.tour_title);
        TextView tourDescription = view.findViewById(R.id.tour_description);

        // If a tour was selected, populate the views with its data
        if (selectedTour != null) {
            // Set the tour image using the resource ID stored in the tour object
            tourImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), selectedTour.getImageResourceId(requireContext())));

            // Set the tour title
            tourTitle.setText(selectedTour.getTitle());

            // Set the tour description
            tourDescription.setText(selectedTour.getDescription());
        }

        // Retrieve the confirm button
        Button confirmButton = view.findViewById(R.id.confirm_button);

        // Set the click event listener for the confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the confirmation fragment
                ConfirmationFragment confirmationFragment = new ConfirmationFragment();

                // Add the selected tour as arguments to the confirmation fragment
                Bundle args = new Bundle();
                args.putSerializable("selectedTour", selectedTour);
                confirmationFragment.setArguments(args);

                // Navigate to the confirmation fragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, confirmationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
