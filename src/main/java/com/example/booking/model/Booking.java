package com.example.booking.model;

import com.example.booking.BookingApplication;
import com.example.booking.enums.BookingStatus;
import com.example.booking.exception.InvalidStateException;
import jdk.jfr.BooleanFlag;
import lombok.Getter;

import java.util.List;

@Getter
public class Booking {

    private String id;
    private Show show;
    private List<Seat> selectedSeats;
    private BookingStatus status;
    private String user;

    public Booking(String id, Show show, List<Seat> selectedSeats, String user) {
        this.id = id;
        this.show = show;
        this.selectedSeats = selectedSeats;
        this.status = BookingStatus.Created;
        this.user = user;
    }

    public boolean isConfirmed(){
        return this.status.equals(BookingStatus.Confirmed);
    }

    public void confirmBooking(Booking booking){

        if(BookingStatus.Created.equals(booking.getStatus())){
            booking.status = BookingStatus.Confirmed;
        }
        else throw new InvalidStateException();
    }
}
