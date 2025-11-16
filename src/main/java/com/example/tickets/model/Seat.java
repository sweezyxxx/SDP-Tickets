package com.example.tickets.model;

public class Seat {

    private final String id;
    private final double basePrice;

    private boolean taken = false;
    private final SeatType type;

    public Seat(String id, double price, SeatType type) {
        this.id = id;
        this.basePrice = price;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public SeatType getType() {
        return type;
    }
}
