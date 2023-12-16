package com.example.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Screen
{

    private String id;
    private String name;
    private Theater theater;
    private List<Seat> seats = new ArrayList<>();


    public Screen(String id, String name, Theater theater) {
        this.id = id;
        this.name = name;
        this.theater = theater;
    }

    public void addSeat(Seat seat){
        this.seats.add(seat);
    }
}
