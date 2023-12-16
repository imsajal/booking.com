package com.example.booking.scenario;

import com.example.booking.exception.SeatPermanentlyBookedException;
import com.example.booking.exception.SeatTemporarilyLockedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Case3 extends TestHelper{

    @BeforeEach
    void setUp(){
        setUpControllers(10, 0);
    }

    @Test
    void Test3() {

        String sajalSidhi = "sajalSidhi";
        String faltuAddmi = "faltuAddmi";
        String screenId = setUpScreenAndTheater("Cinnepolis");
        String movieId = movieController.createMovie("Sexy Movie");
        String showId = showController.createShow(new Date(), screenId, movieId, 3 * 60 * 60);

        List<String> totalSeats = createSeats(2, 10, screenId);

        validateFetchedSeats(showController.getAvailableSeats(showId), totalSeats, new ArrayList<>());

        List<String> seatsSelectedBySajalSidhi =
                Arrays.asList(totalSeats.get(0), totalSeats.get(1), totalSeats.get(2), totalSeats.get(3));

        List<String> seatsSelectedByFaltuAadmi =
                Arrays.asList(totalSeats.get(2), totalSeats.get(3), totalSeats.get(5), totalSeats.get(6));

        String bookingId = bookingController.createBooking(showId, seatsSelectedBySajalSidhi, sajalSidhi);

        Assertions.assertThrows(SeatTemporarilyLockedException.class, () ->
                bookingController.createBooking(showId, seatsSelectedByFaltuAadmi, faltuAddmi));

        paymentController.confirmPayment(sajalSidhi, bookingId);

        Assertions.assertThrows(SeatPermanentlyBookedException.class, () ->
                bookingController.createBooking(showId, seatsSelectedByFaltuAadmi, faltuAddmi));

    }
}
