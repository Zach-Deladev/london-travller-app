package com.example.loginsignupsystem.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Tour;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// This class is a fragment that shows detailed information about a Tour
public class TourInfoFragment extends Fragment {
    // Instance variables
    private Tour currentTour; // The current selected tour
    private boolean isDescriptionExpanded = false; // Is the description text fully displayed?
    private String chosenDate; // The date chosen by the user
    private int chosenTicketNumber; // The number of tickets chosen by the user
    private TextView priceView; // View to display the total price
    private TextView dateView; // View to display the chosen date
    private TextView ticketCountView; // View to display the chosen number of tickets



    @Override
    // This method creates and returns the view hierarchy associated with the fragment.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.fragment_tour_info, container, false);

        // Initialize the TextViews
        priceView = layoutView.findViewById(R.id.price_text_view);
        dateView = layoutView.findViewById(R.id.date_text_view);
        ticketCountView = layoutView.findViewById(R.id.ticket_text_view);

        //Initialise Checkout button
        Button checkoutButton = layoutView.findViewById(R.id.checkout);


        // Check if the fragment was given a bundle of arguments
        if (getArguments() != null) {
            // Get the current tour from the arguments
            currentTour = (Tour) getArguments().getSerializable("selectedTour");
        }

        // If a current tour was provided
        if (currentTour != null) {
            // Initialize the Views
            ImageView tourImageView = layoutView.findViewById(R.id.tour_image);
            TextView tourTitleView = layoutView.findViewById(R.id.tour_title);
            TextView tourDescriptionView = layoutView.findViewById(R.id.tour_description);
            TextView readMoreButton = layoutView.findViewById(R.id.read_more_button);
            Spinner dateSelectionSpinner = layoutView.findViewById(R.id.date_spinner);
            Spinner ticketNumberSpinner = layoutView.findViewById(R.id.number_of_tickets_spinner);

            // Set the image, title, and description of the tour
            tourImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), currentTour.getImageResourceId(requireContext())));
            tourTitleView.setText(currentTour.getTitle());
            tourDescriptionView.setText(currentTour.getDescription());

            // Set a click listener on the read more button to toggle the description view between collapsed and expanded
            readMoreButton.setOnClickListener(v -> {
                isDescriptionExpanded = !isDescriptionExpanded;
                updateDescriptionText(tourDescriptionView, readMoreButton);
            });

            // Initially, display the description in the collapsed state
            updateDescriptionText(tourDescriptionView, readMoreButton);

            // Get the list of possible dates and set up the spinner for date selection
            List<String> dates = fetchDates();
            dates.add(0, "Date Selected: ");
            ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dates);
            dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dateSelectionSpinner.setAdapter(dateAdapter);
            chosenDate = dates.get(0); // Default chosen date is the first in the list
            dateView.setText(chosenDate);
            dateSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                // Handle user selection of a date
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    chosenDate = (String) parent.getItemAtPosition(position);
                    dateView.setText(chosenDate);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            // Set up the spinner for ticket number selection
            List<String> ticketNumbers = new ArrayList<>();
            ticketNumbers.add("0");
            for (int i = 1; i <= 10; i++) {
                ticketNumbers.add(String.valueOf(i));
            }
            ArrayAdapter<String> ticketNumberAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, ticketNumbers);
            ticketNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ticketNumberSpinner.setAdapter(ticketNumberAdapter);
            chosenTicketNumber = Integer.parseInt(ticketNumbers.get(0));
            ticketCountView.setText(String.format(Locale.getDefault(), "No. of Tickets: %s", ticketNumbers.get(0)));
            priceView.setText("£0.00");
            ticketNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                // Handle user selection of a ticket number
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    chosenTicketNumber = Integer.parseInt((String) parent.getItemAtPosition(position));
                    ticketCountView.setText(String.format(Locale.getDefault(), "No. of Tickets: %d", chosenTicketNumber));
                    // Whenever the user selects a new ticket number, update the total price
                    updateDisplayedPrice();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        // Onclick listener for checkout button
        checkoutButton.setOnClickListener(v -> {
            // Store the tour information in a bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedTour", currentTour);
            bundle.putString("chosenDate", chosenDate);
            bundle.putInt("chosenTicketNumber", chosenTicketNumber);

            // Calculate the total price
            double ticketPrice = 19.99;
            double totalPrice = ticketPrice * chosenTicketNumber;
            bundle.putDouble("totalPrice", totalPrice);

            // Create an instance of ConfirmationFragment and set its arguments
            ConfirmationFragment confirmationFragment = new ConfirmationFragment();
            confirmationFragment.setArguments(bundle);

            // Use FragmentTransaction to replace the current fragment with the ConfirmationFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, confirmationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });



        // Return the final view hierarchy.
        return layoutView;
    }

    @Override
    // When the fragment is paused, update the total price. This is good for scenarios where the tour price changes while the fragment is not in the foreground.
    public void onPause() {
        super.onPause();
        updateDisplayedPrice();
    }
    // This method generates a list of tour dates starting from the upcoming Friday to the end of October
    private List<String> fetchDates() {
        // Initializing an empty ArrayList to hold the tour dates
        List<String> dates = new ArrayList<>();

        // Creating a Calendar object to represent the current date
        Calendar calendar = Calendar.getInstance();

        // Getting the current day of the week
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        // Calculating the number of days until the upcoming Friday
        int fridayOffset = Calendar.FRIDAY - today;
        if (fridayOffset < 0) {
            // If today is after Friday, add 7 to the offset to get the number of days until the next Friday
            fridayOffset += 7;
        }
        // Adding the offset to the current date to get the upcoming Friday
        calendar.add(Calendar.DAY_OF_YEAR, fridayOffset);

        // Creating another Calendar object to represent the end date
        Calendar end = Calendar.getInstance();
        // Setting the end date to the last day of October of the current year
        end.set(Calendar.MONTH, Calendar.OCTOBER);
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));

        // Creating a SimpleDateFormat object to format the dates in the "dd-MM-yyyy" pattern
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        // Generating the tour dates from the upcoming Friday until the end of October
        while (calendar.before(end)) {
            // For each weekend (Friday, Saturday, and Sunday)
            for (int i = 0; i < 3; i++) {
                // Format the current date and add it to the list of tour dates
                String formattedDate = sdf.format(calendar.getTime());
                dates.add(formattedDate);
                // Go to the next day
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            // Skip the next four days (Monday to Thursday)
            calendar.add(Calendar.DAY_OF_YEAR, 4);
        }

        // Returning the list of tour dates
        return dates;
    }

    // This method updates the total price displayed on the screen
    private void updateDisplayedPrice() {
        if(chosenTicketNumber != 0) {
            double ticketPrice = 19.99;
            double totalCost = ticketPrice * chosenTicketNumber;
            priceView.setText(String.format(Locale.getDefault(), "Total Price: £%.2f", totalCost));
        } else {
            priceView.setText("Total Cost: £0.00");
        }
    }
    // This method sets the max number of lines for the tour description view, and hides the read more button if the description is short enough

    private void updateDescriptionText(TextView tourDescription, TextView readMoreButton) {
        int maxLines = isDescriptionExpanded ? Integer.MAX_VALUE : 15;
        tourDescription.setMaxLines(maxLines);

        tourDescription.post(() -> {
            if (tourDescription.getLineCount() > 15) {
                readMoreButton.setVisibility(View.VISIBLE);
                readMoreButton.setText(isDescriptionExpanded ? "Read Less" : "Read More");
            } else {
                readMoreButton.setVisibility(View.GONE);
            }
        });
    }
}
