package com.example.tickets.model;

public class Seat {

    private final String id;
    private final double basePrice;
    private boolean taken = false;

    public Seat(String id, double basePrice) {
        this.id = id;
        this.basePrice = basePrice;
    }

    public String getId() { return id; }
    public double getBasePrice() { return basePrice; }

    public boolean isTaken() { return taken; }
    public void setTaken(boolean taken) { this.taken = taken; }

    @Override
    public String toString() {
        return id + " (" + basePrice + "₸)";
    }
}
