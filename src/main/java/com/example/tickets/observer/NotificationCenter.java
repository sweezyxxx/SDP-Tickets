package com.example.tickets.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationCenter {
    private final List<NotificationListener> listeners = new ArrayList<>();

    public void add(NotificationListener l) { listeners.add(l); }

    public void notifyAll(String msg) {
        for (var l : listeners) l.onNotify(msg);
    }
}
