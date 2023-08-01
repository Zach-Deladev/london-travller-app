// ConfirmationFragment.java
package com.example.loginsignupsystem.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Bookings;
import com.example.loginsignupsystem.model.BookingsDao;
import com.example.loginsignupsystem.model.BookingsDaoProvider;
import com.example.loginsignupsystem.model.Tour;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ConfirmationFragment extends Fragment {
    private Tour selectedTour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        // Get the arguments passed from the TourInfoFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            Tour selectedTour = (Tour) bundle.getSerializable("selectedTour");
            String chosenDate = bundle.getString("chosenDate");
            int chosenTicketNumber = bundle.getInt("chosenTicketNumber");
            double totalPrice = getArguments().getDouble("totalPrice");
            // Use selectedTour, chosenDate, and chosenTicketNumber here...
        }
        Button confirmButton = view.findViewById(R.id.btn);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBooking();
            }
        });

        // Retrieve the selected tour from the arguments, if available
        if (getArguments() != null) {
            selectedTour = (Tour) getArguments().getSerializable("selectedTour");
        }

        return view;
    }

    private void confirmBooking() {
        // Retrieve the currently logged-in user's ID
        int userId = getLoggedInUserId();

        // If there is a logged-in user, you can use the userId as needed
        if (userId != -1) {
            // Generate a random booking number
            int randomNumber = generateRandomNumber();


            // Create a new booking with the provided information
            Bookings booking = new Bookings(userId, randomNumber, currentDate, selectedTour.getTitle());

            // Get the instance of BookingsDao
            BookingsDao bookingsDao = BookingsDaoProvider.getInstance(requireContext());

            // Insert the booking into the database
            long result = bookingsDao.addBooking(booking);

            if (result != -1) {
                // Booking was successfully inserted into the database
                // Navigate to the ConfirmationPageFragment
                openConfirmationPageFragment();
            } else {
                // Error occurred while inserting the booking
                Log.e("ConfirmationFragment", "Error inserting booking into database.");
            }
        } else {
            // Error occurred while inserting the booking
            Log.e("ConfirmationFragment", "Error inserting booking into database.");
        }
    }


    private int generateRandomNumber() {
        Random random = new Random();
        int upperLimit = 1000000;
        return random.nextInt(upperLimit);
    }

    // Get the ID of the currently logged-in user
    private int getLoggedInUserId() {
        UsersDao usersDao = UsersDaoProvider.getInstance(getActivity());
        Users loggedInUser = usersDao.getLoggedInUser();
        if (loggedInUser != null) {
            return loggedInUser.getId();
        } else {
            return -1;
        }
    }



    private void openConfirmationPageFragment() {
        // Pass the selected tour to the ConfirmationPageFragment
        Bundle args = new Bundle();
        args.putSerializable("selectedTour", selectedTour);
        ConfirmationPageFragment confirmationPageFragment = new ConfirmationPageFragment();
        confirmationPageFragment.setArguments(args);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, confirmationPageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
