package com.example.tickets.facade;
import com.example.tickets.decorator.BaggageDecorator;
import com.example.tickets.decorator.InsuranceDecorator;
import com.example.tickets.factory.*;
import com.example.tickets.model.Event;
import com.example.tickets.model.Seat;
import com.example.tickets.model.User;
import com.example.tickets.observer.NotificationCenter;
import com.example.tickets.strategy.SeatingStrategy;


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

        // 1 — выбираем место
        Seat seat = event.bookSeat(strategy);

        if (seat == null) {
            center.notifyAll("No seats left for " + event.getName() +
                    " using " + strategy.getClass().getSimpleName());
            return null;
        }

        // 2 — создаём базовый билет
        TicketFactory factory =
                switch (event.getType()) {
                    case Concert -> new ConcertTicketFactory();
                    case Plane -> new PlaneTicketFactory();
                    default -> new CinemaTicketFactory();
                };

        Ticket ticket = factory.create(event, seat);

        if (baggage)   ticket = new BaggageDecorator(ticket);
        if (insurance) ticket = new InsuranceDecorator(ticket);

        center.notifyAll(
                user.getName() + " booked " +
                        seat.getId() + " (" + event.getName() + ")" +
                        " | " + ticket.getDescription() +
                        " | Total: " + ticket.getPrice() + "₸"
        );

        return ticket;
    }
}
