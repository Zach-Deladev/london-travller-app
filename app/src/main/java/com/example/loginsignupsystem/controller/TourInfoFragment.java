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
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Guides;
import com.example.loginsignupsystem.model.Tour;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TourInfoFragment extends Fragment {
    private Tour currentTour;
    private boolean isDescriptionExpanded = false;
    private String chosenDate;
    private int chosenTicketNumber;
    private TextView priceView;
    private TextView dateView;
    private TextView ticketCountView;
    private List<Guides> guides;
    private double totalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_tour_info, container, false);

        // Initialize Views related to pricing and ticket details
        priceView = layoutView.findViewById(R.id.price_text_view);
        dateView = layoutView.findViewById(R.id.date_text_view);
        ticketCountView = layoutView.findViewById(R.id.ticket_text_view);
        Button checkoutButton = layoutView.findViewById(R.id.checkout);

        // Get the selected tour from arguments
        if (getArguments() != null) currentTour = (Tour) getArguments().getSerializable("selectedTour");

        // Set the current tour information if a tour has been selected
        if (currentTour != null) setUpTourInfo(layoutView);

        // Set up checkout button functionality
        checkoutButton.setOnClickListener(v -> handleCheckout());

        return layoutView;
    }

    private void setUpTourInfo(View layoutView) {
        // Initialization and setting of various views related to tour details
        ImageView tourImageView = layoutView.findViewById(R.id.tour_image);
        TextView tourTitleView = layoutView.findViewById(R.id.tour_title);
        TextView tourDescriptionView = layoutView.findViewById(R.id.tour_description);
        TextView readMoreButton = layoutView.findViewById(R.id.read_more_button);
        Spinner dateSelectionSpinner = layoutView.findViewById(R.id.date_spinner);
        Spinner ticketNumberSpinner = layoutView.findViewById(R.id.number_of_tickets_spinner);

        // Set image, title, and description
        tourImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), currentTour.getImageResourceId(requireContext())));
        tourTitleView.setText(currentTour.getTitle());
        tourDescriptionView.setText(currentTour.getDescription());

        // Handle read more/less functionality for the description
        readMoreButton.setOnClickListener(v -> updateDescriptionText(tourDescriptionView, readMoreButton));
        updateDescriptionText(tourDescriptionView, readMoreButton);

        // Populate date selection spinner
        List<String> dates = fetchDates();
        dates.add(0, "Date Selected: ");
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSelectionSpinner.setAdapter(dateAdapter);
        chosenDate = dates.get(0);
        dateView.setText(chosenDate);
        dateSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenDate = (String) parent.getItemAtPosition(position);
                dateView.setText(chosenDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Populate ticket number spinner and update displayed price
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
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenTicketNumber = Integer.parseInt((String) parent.getItemAtPosition(position));
                ticketCountView.setText(String.format(Locale.getDefault(), "No. of Tickets: %d", chosenTicketNumber));
                updateDisplayedPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void handleCheckout() {
        // Validate selections and proceed to the guide selection fragment
        if (!chosenDate.equals("Date Selected: ") && chosenTicketNumber > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedTour", currentTour);
            bundle.putString("chosenDate", chosenDate);
            bundle.putInt("chosenTicketNumber", chosenTicketNumber);
            bundle.putDouble("totalPrice", 19.99 * chosenTicketNumber);

            SelectGuideFragment selectGuideFragment = new SelectGuideFragment();
            selectGuideFragment.setArguments(bundle);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectGuideFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            Toast.makeText(getActivity(), "Please choose a date and ticket number before proceeding.", Toast.LENGTH_LONG).show();
        }
    }

    private void updateDescriptionText(TextView tourDescriptionView, TextView readMoreButton) {
        // Toggle the expanded state of the tour description
        if (isDescriptionExpanded) {
            tourDescriptionView.setMaxLines(Integer.MAX_VALUE);
            readMoreButton.setText(R.string.read_less);
        } else {
            tourDescriptionView.setMaxLines(3);
            readMoreButton.setText(R.string.read_more);
        }
    }

    private List<String> fetchDates() {
        // Fetches the next 7 dates starting from today
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

        for (int i = 0; i < 7; i++) {
            dates.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private void updateDisplayedPrice() {
        // Update the displayed price based on the chosen ticket number
        totalPrice = 19.99 * chosenTicketNumber; // Assuming a fixed price per ticket
        priceView.setText(String.format(Locale.getDefault(), "£%.2f", totalPrice));
    }
}
