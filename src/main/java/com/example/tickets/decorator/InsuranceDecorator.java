package com.example.tickets.decorator;

import com.example.tickets.factory.Ticket;

public class InsuranceDecorator extends TicketDecorator {

    public InsuranceDecorator(Ticket base) {
        super(base);
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " + Insurance";
    }

    @Override
    public double getPrice() {
        return base.getPrice() + 1000;
    }
}
