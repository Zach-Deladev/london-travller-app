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

import com.example.loginsignupsystem.controller.SelectGuideFragment;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignupsystem.R;


import com.example.loginsignupsystem.model.Guides;
import com.example.loginsignupsystem.model.Tour;

import com.example.loginsignupsystem.model.TourDaoProvider;

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

        priceView = layoutView.findViewById(R.id.price_text_view);
        dateView = layoutView.findViewById(R.id.date_text_view);
        ticketCountView = layoutView.findViewById(R.id.ticket_text_view);
        Button checkoutButton = layoutView.findViewById(R.id.checkout);


        if (getArguments() != null) {
            currentTour = (Tour) getArguments().getSerializable("selectedTour");
        }

        if (currentTour != null) {
            ImageView tourImageView = layoutView.findViewById(R.id.tour_image);
            TextView tourTitleView = layoutView.findViewById(R.id.tour_title);
            TextView tourDescriptionView = layoutView.findViewById(R.id.tour_description);
            TextView readMoreButton = layoutView.findViewById(R.id.read_more_button);
            Spinner dateSelectionSpinner = layoutView.findViewById(R.id.date_spinner);
            Spinner ticketNumberSpinner = layoutView.findViewById(R.id.number_of_tickets_spinner);

            tourImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), currentTour.getImageResourceId(requireContext())));
            tourTitleView.setText(currentTour.getTitle());
            tourDescriptionView.setText(currentTour.getDescription());

            readMoreButton.setOnClickListener(v -> {
                isDescriptionExpanded = !isDescriptionExpanded;
                updateDescriptionText(tourDescriptionView, readMoreButton);
            });

            updateDescriptionText(tourDescriptionView, readMoreButton);

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





        checkoutButton.setOnClickListener(v -> {
            // Make sure a date and ticket number other than the default have been selected.
            if (!chosenDate.equals("Date Selected: ") && chosenTicketNumber > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedTour", currentTour);
                bundle.putString("chosenDate", chosenDate);
                bundle.putInt("chosenTicketNumber", chosenTicketNumber);

                double ticketPrice = 19.99;
                double totalPrice = ticketPrice * chosenTicketNumber;
                bundle.putDouble("totalPrice", totalPrice);

                SelectGuideFragment selectGuideFragment = new SelectGuideFragment();
                selectGuideFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, selectGuideFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                // Prompt the user to make a selection.
                Toast.makeText(getActivity(), "Please choose a date and ticket number before proceeding.", Toast.LENGTH_LONG).show();
            }
        });


        return layoutView;
    }

    private void updateDescriptionText(TextView tourDescriptionView, TextView readMoreButton) {
        if (isDescriptionExpanded) {
            tourDescriptionView.setMaxLines(Integer.MAX_VALUE);
            readMoreButton.setText(R.string.read_less);
        } else {
            tourDescriptionView.setMaxLines(3);
            readMoreButton.setText(R.string.read_more);
        }
    }

    private List<String> fetchDates() {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

        for (int i = 0; i < 7; i++) {
            dates.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

    private void updateDisplayedPrice() {
        double ticketPrice = 19.99;
        double totalPrice = ticketPrice * chosenTicketNumber;
        priceView.setText(String.format(Locale.getDefault(), "£%.2f", totalPrice));
    }


}
