package com.example.booking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Show {

    private String id;
    private Screen screen;
    private Date startTime;
    private Integer durationInSeconds;
    private Movie movie;
}
