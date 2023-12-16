package com.example.booking.service;


import com.example.booking.exception.BadRequestException;
import com.example.booking.model.Booking;
import com.example.booking.provider.SeatLockProvider;

import java.util.HashMap;
import java.util.Map;

public class PaymentService {

    private BookingService bookingService;
    private SeatLockProvider seatLockProvider;

    private Integer maximumRetries;
    private Map<Booking, Integer> bookingFailures = new HashMap<>();

    public PaymentService(BookingService bookingService, SeatLockProvider seatLockProvider, Integer maximumRetries) {
        this.bookingService = bookingService;
        this.seatLockProvider = seatLockProvider;
        this.maximumRetries = maximumRetries;
    }

    public void paymentFailed(String user, String bookingId) {

        Booking booking = bookingService.getBooking(bookingId);

        if (!booking.getUser().equals(user)) {
            throw new BadRequestException();
        }

        if(!bookingFailures.containsKey(booking)) bookingFailures.put(booking, 0);

        Integer retryCount = bookingFailures.get(booking)+1;
        bookingFailures.put(booking, retryCount);

        if(retryCount > maximumRetries)
            seatLockProvider.unlockSeats(booking.getShow(), booking.getSelectedSeats(), user);


    }
}
