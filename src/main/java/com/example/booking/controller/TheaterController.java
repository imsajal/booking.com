package com.example.booking.controller;

import com.example.booking.service.TheaterService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TheaterController {

    private TheaterService theaterService;

    public String createTheater(@NonNull final String name){

        return theaterService.createTheater(name).getId();

    }

    public String createScreenInTheater(@NonNull final String theaterId, @NonNull final String screenName){

       return theaterService.createScreenInTheater(theaterId, screenName).getId();
    }

    public String createSeatInScreen(@NonNull final String screenId, @NonNull final Integer row,
                                     @NonNull final Integer number){

        return theaterService.createSeatInScreen(screenId,row,number).getId();

    }


}
