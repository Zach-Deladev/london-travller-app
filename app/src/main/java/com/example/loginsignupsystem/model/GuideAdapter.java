package com.example.loginsignupsystem.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.controller.ConfirmationFragment;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {

    private Context context; // Application context
    private List<Guides> guides; // List of guides
    private String chosenDate; // Selected date
    private int chosenTicketNumber; // Number of chosen tickets
    private double totalPrice; // Total price for the booking
    private Tour selectedTour; // Selected tour

    // Constructor to initialize variables
    public GuideAdapter(Context context, List<Guides> guides, String chosenDate, int chosenTicketNumber, double totalPrice, Tour selectedTour) {
        this.context = context;
        this.guides = guides;
        this.chosenDate = chosenDate;
        this.chosenTicketNumber = chosenTicketNumber;
        this.totalPrice = totalPrice;
        this.selectedTour = selectedTour;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the guide_item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_item, parent, false);
        return new GuideViewHolder(view); // Return the view holder
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Guides guide = guides.get(position); // Get guide at the current position

        // Set the details for the guide
        holder.image.setImageResource(guide.getImage());
        holder.name.setText(guide.getGuidName());
        holder.language.setText(guide.getLanguage());

        // Set the onClickListener for the select button
        holder.selectButton.setOnClickListener(v -> {
            ConfirmationFragment confirmationFragment = new ConfirmationFragment(); // Create a ConfirmationFragment
            Bundle args = new Bundle();

            // Add details to the bundle
            args.putString("chosenDate", chosenDate);
            args.putInt("chosenTicketNumber", chosenTicketNumber);
            args.putDouble("totalPrice", totalPrice);
            args.putSerializable("selectedGuide", guide);
            args.putSerializable("selectedTour", selectedTour);

            confirmationFragment.setArguments(args); // Set the arguments for the fragment

            // Start a fragment transaction to display the ConfirmationFragment
            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, confirmationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return guides.size(); // Return the number of guides
    }

    // ViewHolder class to hold the views
    public static class GuideViewHolder extends RecyclerView.ViewHolder {
        ImageView image; // Image view for guide's image
        TextView name; // Text view for guide's name
        TextView language; // Text view for guide's language
        Button selectButton; // Button to select the guide

        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            language = itemView.findViewById(R.id.language);
            selectButton = itemView.findViewById(R.id.select_button);
        }
    }
}
