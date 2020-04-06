package com.telnov.software_design.event_sourcing.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.telnov.software_design.event_sourcing.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventStorage {

    private final Map<Long, List<Event>> events;

    public EventStorage() {
        this.events = new ConcurrentHashMap<>();
    }

    public List<Event> get(Long id) {
        return events.get(id);
    }

    public boolean empty(Long id) {
        return events.getOrDefault(id, Collections.emptyList()).isEmpty();
    }

    public void save(Long id, Event event) {
        if (!events.containsKey(id)) {
            events.put(id, new ArrayList<>());
        }
        events.get(id).add(event);
    }
}
