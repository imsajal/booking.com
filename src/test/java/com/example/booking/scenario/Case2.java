package com.example.booking.scenario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Case2 extends TestHelper{

    @BeforeEach
    void setUp(){
        setUpControllers(10, 0);
    }

    @Test
    void Test2() {

        String sajalSidhi = "sajalSidhi";
        String faltuAddmi = "faltuAddmi";
        String screenId = setUpScreenAndTheater("Cinnepolis");
        String movieId = movieController.createMovie("Sexy Movie");
        String showId = showController.createShow(new Date(), screenId, movieId, 3 * 60 * 60);

        List<String> totalSeats = createSeats(2, 10, screenId);

        validateFetchedSeats(showController.getAvailableSeats(showId), totalSeats, new ArrayList<>());

        List<String> seatsSelectedBySajalSidhi =
                Arrays.asList(totalSeats.get(0), totalSeats.get(1), totalSeats.get(2), totalSeats.get(3));

        String bookingId = bookingController.createBooking(showId, seatsSelectedBySajalSidhi, sajalSidhi);

        validateFetchedSeats(showController.getAvailableSeats(showId) , totalSeats, seatsSelectedBySajalSidhi);

        paymentController.paymentFailed(sajalSidhi, bookingId);

        validateFetchedSeats(showController.getAvailableSeats(showId) , totalSeats, new ArrayList<>());

    }
}
