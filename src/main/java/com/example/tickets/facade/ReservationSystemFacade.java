package com.example.tickets.facade;

import com.example.tickets.adapter.AirlineApiAdapter;
import com.example.tickets.decorator.*;
import com.example.tickets.factory.*;
import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;
import com.example.tickets.observer.NotificationCenter;
import com.example.tickets.model.User;
import com.example.tickets.strategy.SeatingStrategy;
import com.example.tickets.adapter.ExternalTicketProvider;

import java.util.List;

public class ReservationSystemFacade {

    private final NotificationCenter center;

    public ReservationSystemFacade(NotificationCenter center) {
        this.center = center;
    }

    public Ticket bookTicket(User user,
                             Event event,
                             SeatingStrategy strategy,
                             boolean baggage,
                             boolean insurance) {

        Seat seat = strategy.chooseSeat(event);

        if (seat == null) {
            center.notifyAll("No seats left for " + event.getName()
                    + " with strategy " + strategy.getClass().getSimpleName());
            return null;
        }

        if (!event.bookSeat(seat)) {
            center.notifyAll("Seat " + seat.getId() + " is already taken.");
            return null;
        }

        TicketFactory factory =
                switch (event.getType()) {
                    case CONCERT -> new ConcertTicketFactory();
                    case PLANE -> new PlaneTicketFactory();
                    default -> new CinemaTicketFactory();
                };

        Ticket ticket = factory.create(event, seat);

        if (baggage)  ticket = new BaggageDecorator(ticket);
        if (insurance) ticket = new InsuranceDecorator(ticket);

        center.notifyAll("User " + user.getName()
                + " booked " + seat.getId()
                + " for event " + event.getName());

        return ticket;
    }
    private ExternalTicketProvider externalProvider = new AirlineApiAdapter();

    public List<Event> loadExternalEvents() {
        return externalProvider.fetchExternalEvents();
    }

}
