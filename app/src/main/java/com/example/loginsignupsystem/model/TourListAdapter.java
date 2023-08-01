package com.example.loginsignupsystem.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.controller.TourInfoFragment;

import java.util.List;

// This is a custom ArrayAdapter for the Tour objects. ArrayAdapter is a type of Adapter that works with a List of data.
public class TourListAdapter extends ArrayAdapter<Tour> {

    // Context of the application.
    private final Context context;

    // Constructor of our TourListAdapter that takes the current context and a list of tours.
    public TourListAdapter(Context context, List<Tour> tours) {
        super(context, R.layout.list_item, tours);
        this.context = context;
    }

    // Method for generating each item in the list.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // If convertView is null, it means we are creating this list item view for the first time.
        if (convertView == null) {
            // Inflate the layout for the list item.
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

            // Create a new ViewHolder to hold references to the list item's views (to avoid calling findViewById repeatedly).
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.image);
            viewHolder.titleView = convertView.findViewById(R.id.title);
            viewHolder.subtitleView = convertView.findViewById(R.id.subtitle);
            viewHolder.selectButton = convertView.findViewById(R.id.select_button);

            // Store the ViewHolder in the convertView.
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, it means we are recycling a view. Retrieve the ViewHolder from the convertView.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the tour for the current list item.
        Tour tour = getItem(position);

        // Update the views of the ViewHolder with the data from the current tour.
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(context, tour.getImageResourceId(context)));
        viewHolder.titleView.setText(tour.getTitle());
        viewHolder.subtitleView.setText(tour.getSubTitle());

        // Set an OnClickListener for the select button, to open the TourInfoFragment for the selected tour when clicked.
        viewHolder.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of TourInfoFragment.
                TourInfoFragment tourInfoFragment = new TourInfoFragment();
                // Create a bundle to hold the selected tour.
                Bundle bundle = new Bundle();
                // Add the selected tour to the bundle.
                bundle.putSerializable("selectedTour", tour);
                // Set the arguments for the fragment using the bundle.
                tourInfoFragment.setArguments(bundle);
                // Begin a FragmentTransaction to open the TourInfoFragment.
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                // Replace the current fragment with the TourInfoFragment.
                transaction.replace(R.id.fragment_container, tourInfoFragment);
                // Add the transaction to the back stack, so the user can navigate back.
                transaction.addToBackStack(null);
                // Commit the transaction.
                transaction.commit();
            }
        });

        // Return the updated convertView.
        return convertView;
    }

    // A ViewHolder holds references to the views in each list item layout.
    // It avoids calling findViewById repeatedly, improving performance.
    static class ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView subtitleView;
        Button selectButton;
    }
}
