package com.example.loginsignupsystem.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Bookings;

import java.util.List;

public class BookingsListAdapter extends ArrayAdapter<Bookings> {

    private final Context context; // Context reference

    public BookingsListAdapter(Context context, List<Bookings> bookings) {
        super(context, R.layout.booking_list_item, bookings);
        this.context = context; // Initialize context
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the view if convertView is null
            convertView = LayoutInflater.from(context).inflate(R.layout.booking_list_item, parent, false);

            // Initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.tourView = convertView.findViewById(R.id.tour);
            viewHolder.dateView = convertView.findViewById(R.id.date);
            viewHolder.ticketsView = convertView.findViewById(R.id.tickets);
            viewHolder.priceView = convertView.findViewById(R.id.price);
            viewHolder.refView = convertView.findViewById(R.id.ref);

            convertView.setTag(viewHolder); // Set the viewHolder as a tag for the convertView
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // Retrieve the viewHolder from the convertView's tag
        }

        Bookings booking = getItem(position); // Get the booking for the current position

        // Set the view content
        viewHolder.tourView.setText("Tour: " + booking.getTour());
        viewHolder.dateView.setText("Date: " + booking.getDate());
        viewHolder.ticketsView.setText("Number of tickets: " + booking.getTickets()); // Using getTickets for the number of tickets
        viewHolder.priceView.setText("Total Price: " + booking.getPrice());
        viewHolder.refView.setText("Booking Reference: " + booking.getReference());

        return convertView; // Return the constructed view
    }

    @Override
    public Bookings getItem(int position) {
        return super.getItem(position); // Correctly returning the item at the given position
    }

    // ViewHolder class to hold the view references
    static class ViewHolder {
        TextView tourView; // View to display the tour name
        TextView dateView; // View to display the date
        TextView ticketsView; // View to display the number of tickets
        TextView priceView; // View to display the total price
        TextView refView; // View to display the booking reference
    }
}
