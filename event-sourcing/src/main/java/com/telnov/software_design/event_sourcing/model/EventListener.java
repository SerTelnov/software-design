package com.telnov.software_design.event_sourcing.model;

public interface EventListener {
    void handleEvent(long accountId, Event event);
}
