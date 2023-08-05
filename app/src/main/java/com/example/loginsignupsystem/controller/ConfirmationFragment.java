package com.example.loginsignupsystem.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Bookings;
import com.example.loginsignupsystem.model.BookingsDao;
import com.example.loginsignupsystem.model.BookingsDaoProvider;
import com.example.loginsignupsystem.model.Guides;
import com.example.loginsignupsystem.model.Tour;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoProvider;

import java.util.Random;

public class ConfirmationFragment extends Fragment {
    private Tour selectedTour;
    private Guides selectedGuide;
    private String chosenDate;
    private double totalPrice; // Make totalPrice an instance variable

    private TextView date;
    private TextView ticketnum;
    private TextView price;
    private TextView guide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ConfirmationFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        date = view.findViewById(R.id.date);
        ticketnum = view.findViewById(R.id.ticketnum);
        price = view.findViewById(R.id.price);
        guide = view.findViewById(R.id.guide);

        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedTour = (Tour) bundle.getSerializable("selectedTour");
            chosenDate = bundle.getString("chosenDate");
            int chosenTicketNumber = bundle.getInt("chosenTicketNumber");
            totalPrice = bundle.getDouble("totalPrice"); // Set the totalPrice variable
            selectedGuide = (Guides) bundle.getSerializable("selectedGuide");

            if (selectedGuide != null) {
                Log.d("ConfirmationFragment", "Selected guide: " + selectedGuide.getGuidName());
                guide.setText("Guide: " + selectedGuide.getGuidName());
            }

            date.setText("Date Chosen: " + chosenDate);
            ticketnum.setText("Ticket Number: " + chosenTicketNumber);
            price.setText("Total Price: " + String.format("£%.2f", totalPrice));
        }

        Button confirmButton = view.findViewById(R.id.btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBooking();
            }
        });

        if (getArguments() != null) {
            selectedTour = (Tour) getArguments().getSerializable("selectedTour");
        }

        return view;
    }

    private void confirmBooking() {
        int userId = getLoggedInUserId();
        if (userId != -1) {
            int randomNumber = generateRandomNumber();
            Bookings booking = new Bookings(userId, randomNumber, chosenDate, selectedTour.getTitle(), totalPrice); // Add totalPrice as argument
            BookingsDao bookingsDao = BookingsDaoProvider.getInstance(requireContext());
            long result = bookingsDao.addBooking(booking);

            if (result != -1) {
                openConfirmationPageFragment();
            } else {
                Log.e("ConfirmationFragment", "Error inserting booking into database.");
            }
        } else {
            Log.e("ConfirmationFragment", "Error inserting booking into database.");
        }
    }

    private int generateRandomNumber() {
        Random random = new Random();
        int upperLimit = 1000000;
        return random.nextInt(upperLimit);
    }

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
