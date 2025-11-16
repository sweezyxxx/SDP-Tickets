package com.example.tickets.strategy;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;
import com.example.tickets.model.SeatType;

import java.util.Comparator;

public class CheapestSeatStrategy implements SeatingStrategy {
    @Override
    public Seat chooseSeat(Event event) {
        return event.getFreeSeatsByType(SeatType.REGULAR)
                .stream()
                .min(Comparator.comparingDouble(Seat::getBasePrice))
                .orElse(null);
    }
}
