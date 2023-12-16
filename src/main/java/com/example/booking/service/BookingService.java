package com.example.booking.service;

import com.example.booking.enums.BookingStatus;
import com.example.booking.exception.BadRequestException;
import com.example.booking.exception.NotFoundException;
import com.example.booking.exception.SeatPermanentlyBookedException;
import com.example.booking.model.Booking;
import com.example.booking.model.Seat;
import com.example.booking.model.Show;
import com.example.booking.provider.SeatLockProvider;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class BookingService {

    Map<String, Booking> bookings = new HashMap<>();

    private ShowService showService;

    private TheaterService theaterService;

    private SeatLockProvider seatLockProvider;

    public BookingService(ShowService showService, TheaterService theaterService, SeatLockProvider seatLockProvider) {
        this.showService = showService;
        this.theaterService = theaterService;
        this.seatLockProvider = seatLockProvider;
    }

    public Booking getBooking(@NonNull final String bookingId) {
        if (!bookings.containsKey(bookingId)) {
            throw new NotFoundException();
        }
        return bookings.get(bookingId);
    }

    public List<Seat> getBookedSeats(Show show) {

        return bookings.values().stream().filter(booking ->
                        show.getId().equalsIgnoreCase(booking.getShow().getId())
                                && booking.isConfirmed()).map(Booking::getSelectedSeats)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void confirmPayment(String user, String bookingId) {

        Booking booking = bookings.get(bookingId);

        if (!booking.getUser().equals(user)) {
            throw new BadRequestException();
        }

        for(Seat seat : booking.getSelectedSeats()){
            if(!seatLockProvider.validateLockForUser(booking.getShow(), seat, user))
                throw new BadRequestException();
        }

        booking.confirmBooking(booking);

    }

    public Booking createBooking(String showId, List<String> seatIds, String user) {
        Show show = showService.getShow(showId);
        List<Seat> seats = seatIds.stream().map(theaterService::getSeat).collect(Collectors.toList());


        if (isAnySeatAlreadyBooked(show, seats, user)) throw new SeatPermanentlyBookedException();

        seatLockProvider.lockSeats(show, seats, user);
        String id = UUID.randomUUID().toString();
        Booking booking = new Booking(id, show, seats, user);
        bookings.put(id, booking);
        return booking;
    }

    private boolean isAnySeatAlreadyBooked(Show show, List<Seat> seats, String user) {
        List<Seat> bookedSeats = getBookedSeats(show);
        for(Seat seat :seats){
            if(bookedSeats.contains(seat)) return true;
        }
        return false;
    }
}
