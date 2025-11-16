package com.example.tickets.decorator;

import com.example.tickets.factory.Ticket;

public abstract class TicketDecorator implements Ticket {
    protected final Ticket base;

    public TicketDecorator(Ticket base) {
        this.base = base;
    }
}
