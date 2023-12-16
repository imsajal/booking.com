package com.example.booking.service;


import com.example.booking.exception.NotFoundException;
import com.example.booking.model.Movie;
import com.example.booking.model.Screen;
import com.example.booking.model.Show;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShowService {

    private MovieService movieService;
    private TheaterService theaterService;

    private Map<String, Show> shows = new HashMap<>();


    public ShowService(MovieService movieService, TheaterService theaterService) {
        this.movieService = movieService;
        this.theaterService = theaterService;
    }

    public Show createShow(Date startTime, String screenId, String movieId, Integer durationInSeconds) {

        String id = UUID.randomUUID().toString();
        Screen screen = theaterService.getScreen(screenId);
        Movie movie = movieService.getMovie(movieId);
        Show show = new Show(id, screen, startTime, durationInSeconds, movie);

        shows.put(id, show);

        return show;
    }

    public Show getShow(String showId) {
        if (!shows.containsKey(showId))
            throw new NotFoundException();
        return shows.get(showId);
    }
}
