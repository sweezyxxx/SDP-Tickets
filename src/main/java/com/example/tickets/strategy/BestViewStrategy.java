package com.example.tickets.strategy;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;
import com.example.tickets.model.SeatType;

import java.util.List;

public class BestViewStrategy implements SeatingStrategy {
    @Override
    public Seat chooseSeat(Event event) {
        List<Seat> seats = event.getFreeSeatsByType(SeatType.BEST_VIEW);
        if (seats.isEmpty()) return null;
        // можно взять середину, но достаточно первого
        return seats.get(0);
    }
}
