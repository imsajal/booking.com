package com.example.booking.scenario;


import com.example.booking.controller.*;
import com.example.booking.model.Seat;
import com.example.booking.provider.InMemeorySeatLockProvider;
import com.example.booking.provider.SeatLockProvider;
import com.example.booking.service.*;
import org.assertj.core.api.Assertions;
import org.junit.platform.commons.util.CollectionUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    protected MovieController movieController;
    protected BookingController bookingController;
    protected PaymentController paymentController;
    protected ShowController showController;
    protected TheaterController theaterController;

    protected void setUpControllers(Integer lockTimeOut, Integer maxPaymentRetries){

        SeatLockProvider seatLockProvider = new InMemeorySeatLockProvider(lockTimeOut);
        MovieService movieService = new MovieService();
        TheaterService theaterService = new TheaterService();
        ShowService showService = new ShowService(movieService, theaterService);
        BookingService bookingService = new BookingService(showService, theaterService, seatLockProvider);
        PaymentService paymentService = new PaymentService(bookingService, seatLockProvider, maxPaymentRetries);
        SeatAvailabilityService seatAvailabilityService =
                new SeatAvailabilityService(bookingService, seatLockProvider, showService);

        movieController = new MovieController(movieService);
        bookingController = new BookingController(bookingService);
        paymentController = new PaymentController(paymentService, bookingService);
        showController = new ShowController(showService, seatAvailabilityService);
        theaterController = new TheaterController(theaterService);

    }

    protected String setUpScreenAndTheater(String theaterName){

        String theaterId = theaterController.createTheater(theaterName);
        return theaterController.createScreenInTheater(theaterId, "1");
    }

    protected List<String> createSeats(int numberOfRows, int totalSeatInRow, String screenId){
        List<String> seatIds = new ArrayList<>();
        for(int row = 0; row < numberOfRows ; row++){

            for(int seatNumber = 0 ; seatNumber < totalSeatInRow; seatNumber++){

                seatIds.add(theaterController.createSeatInScreen(screenId, row, seatNumber));
            }
        }
        return seatIds;
    }

    protected void validateFetchedSeats(List<String> fetchedSeats, List<String> allSeats, List<String> excludedSeats){

        for (String seat: excludedSeats)
            Assert.isTrue(!fetchedSeats.contains(seat), () -> "Seat not Excluded in fetched Seats");

        for(String seat : allSeats){

            if(!excludedSeats.contains(seat))
                Assert.isTrue(fetchedSeats.contains(seat), () -> "Seat not present in fetched Seats");
        }

    }

}
