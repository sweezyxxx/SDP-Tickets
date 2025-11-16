package com.example.tickets.factory;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;

public class CinemaTicketFactory implements TicketFactory {

    @Override
    public Ticket create(Event event, Seat seat) {
        return new BaseTicket(
                "Cinema ticket — seat " + seat.getId(),
                seat.getBasePrice()
        );
    }
}
