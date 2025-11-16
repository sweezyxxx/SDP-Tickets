package com.example.tickets.model;

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

    public String getName() {
        return name;
    }

    public EventType getType() {
        return type;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Seat> getFreeSeats() {
        List<Seat> free = new ArrayList<>();
        for (Seat s : seats) {
            if (!s.isTaken()) {
                free.add(s);
            }
        }
        return free;
    }

    public boolean bookSeat(Seat seat) {
        if (!seat.isTaken()) {
            seat.setTaken(true);
            return true;
        }
        return false;
    }

    public List<Seat> getFreeSeatsByType(SeatType type) {
        List<Seat> list = new ArrayList<>();
        for (Seat s : seats) {
            if (!s.isTaken() && s.getType() == type) {
                list.add(s);
            }
        }
        return list;
    }

}
