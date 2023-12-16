package com.example.booking.service;

import com.example.booking.exception.NotFoundException;
import com.example.booking.model.Movie;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Handler;

public class MovieService {

     private Map<String, Movie> movies = new HashMap<>();

     public Movie getMovie(String movieId){

         if(movies.containsKey(movieId)) return movies.get(movieId);
         else throw new NotFoundException();

     }

    public Movie createMovie(String movieName) {

         String id = UUID.randomUUID().toString();
         Movie movie = new Movie(id,movieName);

         movies.put(id, movie);
         return movie;
    }
}
