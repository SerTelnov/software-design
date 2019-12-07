package com.telnov.software_design;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EventsStatisticImpl implements EventsStatistic {

    private final Map<String, Deque<Instant>> events;
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        events = new HashMap<>();
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        if (!events.containsKey(name)) {
            events.put(name, new ArrayDeque<>());
        }

        events.get(name).add(clock.instant());
    }

    @Override
    public long getEventStatisticByName(String name) {
        clearOldStatistic();
        return (long) events.get(name).size();
    }

    @Override
    public Map<Instant, List<String>> getAllEventStatistic() {
        clearOldStatistic();
        Map<Instant, List<String>> eventPerMinMap = new TreeMap<>();

        events.forEach((name, eventTimes) -> eventTimes
                .forEach(time -> {
                    if (!eventPerMinMap.containsKey(time)) {
                        eventPerMinMap.put(time, new ArrayList<>());
                    }
                    eventPerMinMap.get(time).add(name);
                })
        );

        return eventPerMinMap;
    }

    @Override
    public void printStatistic() {
        getAllEventStatistic().forEach((time, name) ->
                System.out.println("At " + time + " was event " + name)
        );
    }

    private void clearOldStatistic() {
        var hourAgoInstant = clock.instant().minus(1L, ChronoUnit.HOURS);
        events.forEach((name, queue) -> {
            while (!queue.isEmpty()) {
                Instant last = queue.peek();
                if (last.isBefore(hourAgoInstant)) {
                    queue.remove();
                } else {
                    break;
                }
            }
        });
    }
}
