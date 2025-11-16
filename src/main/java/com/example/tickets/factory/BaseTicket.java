package com.example.tickets.factory;

public class BaseTicket implements Ticket {
    private final String desc;
    private final double price;

    public BaseTicket(String desc, double price) {
        this.desc = desc;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
