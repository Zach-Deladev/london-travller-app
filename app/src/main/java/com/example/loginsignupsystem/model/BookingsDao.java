package com.example.loginsignupsystem.model;

import java.util.List;



import java.util.List;

public interface BookingsDao {

    long addBooking(Bookings booking);

    Bookings getBookingById(int id);

    List<Bookings> getAllBookings();

    List<Bookings> getBookingsByUserId(int userId);
}
