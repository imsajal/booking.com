package com.example.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@Getter
public class SeatLock {

    private Seat seat;
    private Date startTime;
    private Show show;
    private String lockedBY;
    private Integer timeoutThreshold;

    public boolean isLockExpired(){
        Instant currentInstant = new Date().toInstant();
        Instant lockInstant = startTime.toInstant().plusSeconds(timeoutThreshold);

        return lockInstant.isBefore(currentInstant);

    }


}
