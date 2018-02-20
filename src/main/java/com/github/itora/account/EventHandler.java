package com.github.itora.account;

import com.github.itora.event.Event;

public interface EventHandler {

    void accept(Event event);

    default void accept(Event... events) {
        for (Event event : events) {
            accept(event);
        }
    }
}
