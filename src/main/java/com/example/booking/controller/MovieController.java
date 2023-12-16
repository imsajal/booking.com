package com.example.booking.controller;

import com.example.booking.service.MovieService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    public String createMovie(String movieName){
        return movieService.createMovie(movieName).getId();
    }


}
