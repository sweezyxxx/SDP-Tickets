package com.example.tickets.adapter;

import com.example.tickets.model.Event;
import com.example.tickets.model.EventType;

import java.util.List;
import java.util.stream.Collectors;

public class AirlineApiAdapter implements ExternalTicketProvider {

    private final MockAirlineApi api = new MockAirlineApi();

    @Override
    public List<Event> fetchExternalEvents() {
        return api.getFlights().stream()
                .map(name -> new Event(name, EventType.PLANE))
                .collect(Collectors.toList());
    }
}
