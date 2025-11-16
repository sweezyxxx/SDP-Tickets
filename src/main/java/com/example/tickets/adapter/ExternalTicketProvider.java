package com.example.tickets.adapter;

import com.example.tickets.model.Event;

import java.util.List;

public interface ExternalTicketProvider {
    List<Event> fetchExternalEvents();
}
