package com.example.tickets.model;

import com.example.tickets.strategy.SeatingStrategy;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final String name;
    private final EventType type;
    private final List<Seat> seats = new ArrayList<>();

    public Event(String name, EventType type) {
        this.name = name;
        this.type = type;
    }

    public void addSeat(Seat s) { seats.add(s); }

    public List<Seat> getSeats() { return seats; }


    public Seat bookSeat(SeatingStrategy strategy) {
        Seat chosen = strategy.chooseSeat(this);
        if (chosen != null && !chosen.isTaken()) {
            chosen.setTaken(true);
            return chosen;
        }
        return null;
    }


    public String getName() { return name; }
    public EventType getType() { return type; }

    public String toString() { return name; }
}

