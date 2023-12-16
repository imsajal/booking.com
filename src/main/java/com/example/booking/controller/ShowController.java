package com.example.booking.controller;

import com.example.booking.model.Seat;
import com.example.booking.model.Show;
import com.example.booking.service.MovieService;
import com.example.booking.service.SeatAvailabilityService;
import com.example.booking.service.ShowService;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ShowController {


    private ShowService showService;
    private SeatAvailabilityService seatAvailabilityService;

    public String createShow(Date startTime, String screenId, String movieId, Integer durationInSeconds){

       return showService.createShow(startTime, screenId, movieId, durationInSeconds).getId();

    }

    public List<String> getAvailableSeats(String showId){
         return seatAvailabilityService.getAvailableSeats(showId).stream().map(Seat::getId).collect(Collectors.toList());
    }

}
