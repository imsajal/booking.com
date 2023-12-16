package com.example.booking.provider;

import com.example.booking.exception.SeatTemporarilyLockedException;
import com.example.booking.model.Seat;
import com.example.booking.model.SeatLock;
import com.example.booking.model.Show;
import java.util.*;


public class InMemeorySeatLockProvider implements SeatLockProvider {

    private Map<Show, Map<Seat, SeatLock>> locks = new HashMap<>();

    private Integer lockTimeOutThreshold;


    public InMemeorySeatLockProvider(Integer lockTimeOutThreshold) {
        this.lockTimeOutThreshold = lockTimeOutThreshold;
    }

    @Override
    synchronized public void lockSeats(Show show, List<Seat> seats, String user) {
        for (Seat seat : seats) {

            if (isSeatLocked(show, seat)) throw new SeatTemporarilyLockedException();
        }

        for (Seat seat : seats) {

            if (!locks.containsKey(show)) locks.put(show, new HashMap<>());
            SeatLock seatLock = new SeatLock(seat, new Date(), show, user, lockTimeOutThreshold);
            locks.get(show).put(seat, seatLock);

        }
    }

    private boolean isSeatLocked(Show show, Seat seat) {

        return locks.containsKey(show) && locks.get(show).containsKey(seat)
                && !locks.get(show).get(seat).isLockExpired();
    }

    @Override
    public void unlockSeats(Show show, List<Seat> seats, String user) {

        for(Seat seat: seats){
            if(validateLockForUser(show, seat, user)){
                locks.get(show).remove(seat);
            }
        }

    }

    @Override
    public List<Seat> getLockedSeats(Show show) {

        List<Seat> seats = new ArrayList<>();
        if(locks.containsKey(show)){
            for(Seat seat : locks.get(show).keySet()){
                if(isSeatLocked(show, seat)) seats.add(seat);
            }
        }

        return seats;
    }

    @Override
    public boolean validateLockForUser(Show show, Seat seat, String user) {

        return locks.containsKey(show) && locks.get(show).containsKey(seat)
                && locks.get(show).get(seat).getLockedBY().equalsIgnoreCase(user);
    }
}
