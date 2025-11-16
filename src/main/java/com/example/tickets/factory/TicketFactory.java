package com.example.tickets.factory;

import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;

public interface TicketFactory {
    Ticket create(Event event, Seat seat);
}
