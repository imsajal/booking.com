package com.example.booking.service;

import com.example.booking.model.Seat;
import com.example.booking.model.Show;
import com.example.booking.provider.InMemeorySeatLockProvider;
import com.example.booking.provider.SeatLockProvider;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class SeatAvailabilityService {

    private BookingService bookingService;
    private SeatLockProvider inMemeorySeatLockProvider;
    private ShowService showService;

    public List<Seat> getAvailableSeats(String showId) {

        Show show = showService.getShow(showId);
        List<Seat> totalSeats = show.getScreen().getSeats();

        List<Seat> unavailableSeats = inMemeorySeatLockProvider.getLockedSeats(show);
        unavailableSeats.addAll(bookingService.getBookedSeats(show));

        List<Seat> availableSeats = new ArrayList<>(totalSeats);
        availableSeats.removeAll(unavailableSeats);
        return availableSeats;
    }
}
