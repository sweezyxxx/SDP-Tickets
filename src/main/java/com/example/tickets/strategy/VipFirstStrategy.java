package com.example.tickets.strategy;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;
import com.example.tickets.model.SeatType;

import java.util.List;

public class VipFirstStrategy implements SeatingStrategy {
    @Override
    public Seat chooseSeat(Event event) {
        List<Seat> seats = event.getFreeSeatsByType(SeatType.VIP);
        if (seats.isEmpty()) return null;
        return seats.get(0);
    }
}
