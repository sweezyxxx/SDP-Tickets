package com.example.tickets.decorator;

import com.example.tickets.factory.Ticket;

public class BaggageDecorator extends TicketDecorator {

    public BaggageDecorator(Ticket base) {
        super(base);
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " + Baggage";
    }

    @Override
    public double getPrice() {
        return base.getPrice() + 3000;
    }
}
