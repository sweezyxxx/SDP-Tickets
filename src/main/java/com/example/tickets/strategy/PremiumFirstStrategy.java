package com.example.tickets.strategy;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;

import java.util.Comparator;

public class PremiumFirstStrategy implements SeatingStrategy {

    @Override
    public Seat chooseSeat(Event event) {

        return event.getSeats().stream()
                .filter(s -> !s.isTaken())
                .max(Comparator.comparingDouble(Seat::getBasePrice))
                .orElse(null);
    }
}

