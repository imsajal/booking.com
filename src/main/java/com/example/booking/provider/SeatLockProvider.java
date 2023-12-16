package com.example.booking.provider;

import com.example.booking.model.Seat;
import com.example.booking.model.Show;

import java.util.List;

public interface SeatLockProvider {

    void lockSeats(Show show, List<Seat> seats, String user);

    void unlockSeats(Show show, List<Seat> seats, String user);

    List<Seat> getLockedSeats(Show show);

    boolean validateLockForUser(Show show, Seat seat, String user);
}
