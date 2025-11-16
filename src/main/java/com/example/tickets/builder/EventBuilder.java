package com.example.tickets.builder;

import com.example.tickets.model.Event;
import com.example.tickets.model.EventType;
import com.example.tickets.model.Seat;

public class EventBuilder {

    private final Event event;

    public EventBuilder(String name, EventType type) {
        this.event = new Event(name, type);
    }

    /** Добавляет ряд мест: row — 'A', count — кол-во, price — цена */
    public EventBuilder addRow(char row, int count, double price) {
        for (int i = 1; i <= count; i++) {
            String id = row + "" + i;
            event.addSeat(new Seat(id, price));
        }
        return this;
    }

    public Event build() {
        return event;
    }
}
