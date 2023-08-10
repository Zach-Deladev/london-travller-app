package com.example.loginsignupsystem.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Bookings;
import com.example.loginsignupsystem.model.BookingsDao;
import com.example.loginsignupsystem.model.BookingsDaoProvider;
import com.example.loginsignupsystem.model.BookingsListAdapter;
import java.util.List;

public class
BookingsListFragment extends Fragment {
    private ListView listView;
    private BookingsDao bookingDao; // You'll need to define this interface

    public BookingsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookings_list, container, false);
        // Create instance of booking dao provider to interact with the booking dao interface
        bookingDao = BookingsDaoProvider.getInstance(getContext()); // You'll need to define this class
        // Define ListView
        listView = view.findViewById(R.id.bookings_list);
        // Retrieve bookings from the database
        List<Bookings> bookings = bookingDao.getAllBookings(); // Define this method in BookingDao
        // Create and set the custom adapter
        BookingsListAdapter adapter = new BookingsListAdapter(getActivity(), bookings); // You'll need to create this class
        listView.setAdapter(adapter);

        return view;
    }
}
