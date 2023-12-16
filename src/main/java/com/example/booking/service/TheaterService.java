package com.example.booking.service;

import com.example.booking.exception.NotFoundException;
import com.example.booking.model.Screen;
import com.example.booking.model.Seat;
import com.example.booking.model.Theater;

import java.util.*;


public class TheaterService {

    private Map<String, Theater> theaters = new HashMap<>();
    private Map<String, Screen> screens = new HashMap<>();
    private Map<String, Seat> seats = new HashMap<>();


    public Theater createTheater(String name) {
        String id = UUID.randomUUID().toString();
        Theater theater = new Theater(id, name);
        theaters.put(id, theater);
        return theater;
    }

    public Screen getScreen(String screenId){

        if(screens.containsKey(screenId)) return screens.get(screenId);

        else throw new NotFoundException();
    }

    public Seat getSeat(String seatId){

        if(seats.containsKey(seatId)) return seats.get(seatId);

        else throw new NotFoundException();
    }

    public Theater getTheater(String theaterId){

        if(theaters.containsKey(theaterId)) return theaters.get(theaterId);

        else throw new NotFoundException();
    }

    public Screen createScreenInTheater(String theaterId, String screenName) {

        Theater theater = null;

        if (theaters.containsKey(theaterId))
            theater = theaters.get(theaterId);
        else throw new NotFoundException();


        String id = UUID.randomUUID().toString();
        Screen screen = new Screen(id, screenName, theater);

        screens.put(id, screen);
        theater.getScreens().add(screen);

        return screen;

    }

    public Seat createSeatInScreen(String screenId, Integer row, Integer number) {

        Screen screen = null;

        if (screens.containsKey(screenId))
            screen = screens.get(screenId);
        else throw new NotFoundException();

        String id = UUID.randomUUID().toString();
        Seat seat = new Seat(id,row,number);

        seats.put(id,seat);
        screen.getSeats().add(seat);

        return seat;
    }
}
