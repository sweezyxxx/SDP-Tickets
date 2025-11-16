package com.example.tickets.factory;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;

public class PlaneTicketFactory implements TicketFactory {

    @Override
    public Ticket create(Event event, Seat seat) {
        return new BaseTicket(
                "Plane ticket — seat " + seat.getId(),
                seat.getBasePrice()
        );
    }
}
