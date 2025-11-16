package com.example.tickets.strategy;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;

public interface SeatingStrategy {
    Seat chooseSeat(Event event);
}
