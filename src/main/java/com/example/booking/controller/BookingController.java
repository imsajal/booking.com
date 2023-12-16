package com.example.booking.controller;


import com.example.booking.service.BookingService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    public String createBooking(String showId, List<String> seatIds, String user){

      return bookingService.createBooking(showId,seatIds, user).getId();
    }
}
