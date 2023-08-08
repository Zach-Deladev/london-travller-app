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

    private final Context context;

    public BookingsListAdapter(Context context, List<Bookings> bookings) {
        super(context, R.layout.booking_list_item, bookings);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.booking_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tourView = convertView.findViewById(R.id.tour);
            viewHolder.dateView = convertView.findViewById(R.id.date);
            viewHolder.ticketsView = convertView.findViewById(R.id.tickets);
            viewHolder.priceView = convertView.findViewById(R.id.price);
            viewHolder.refView = convertView.findViewById(R.id.ref);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Bookings booking = getItem(position);

        viewHolder.tourView.setText("Tour: " + booking.getTour());
        viewHolder.dateView.setText("Date: " + booking.getDate());
        viewHolder.ticketsView.setText("Number of tickets: " + booking.getTickets()); // Using getTickets for the number of tickets
        viewHolder.priceView.setText("Total Price: " + booking.getPrice());
        viewHolder.refView.setText("Booking Reference: " + booking.getReference());

        return convertView;
    }

    @Override
    public Bookings getItem(int position) {
        return super.getItem(position); // Correctly returning the item at the given position
    }

    static class ViewHolder {
        TextView tourView;
        TextView dateView;
        TextView ticketsView;
        TextView priceView;
        TextView refView;
    }
}
