package com.telnov.software_design;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class EventsStatisticImplTest {

    private ClockStub clock;
    private EventsStatistic eventsStatistic;

    @BeforeEach
    void setup() {
        clock = new ClockStub(Instant.now());
        eventsStatistic = new EventsStatisticImpl(clock);
    }

    @Test
    void getStatisticByNameTest() {
        addSomeEvents(List.of("Event1", "Event2", "Event1"));
        assertThat(eventsStatistic.getEventStatisticByName("Event1"))
                .isEqualTo(2);
    }

    @Test
    void getStatisticByNameAfterHourTest() {
        addSomeEvents(List.of("Event1", "Event1"));
        clock.updNow(Instant.now().plus(1, ChronoUnit.HOURS));

        addSomeEvents(List.of("Event1"));
        assertThat(eventsStatistic.getEventStatisticByName("Event1"))
                .isEqualTo(1);
    }

    @Test
    void getAllStatisticTest() {
        addSomeEvents(List.of("Event1", "Event2"));
        var time1 = clock.instant();
        clock.updNow(Instant.now().plus(1, ChronoUnit.MINUTES));
        var time2 = clock.instant();
        addSomeEvents(List.of("Event3"));

        Map<Instant, List<String>> stat = eventsStatistic.getAllEventStatistic();
        assertThat(stat)
                .containsEntry(time1, List.of("Event2", "Event1"));
        assertThat(stat)
                .containsEntry(time2, List.of("Event3"));
    }

    @Test
    void getAllStatisticAfterHourTest() {
        addSomeEvents(List.of("Event1", "Event2"));
        clock.updNow(Instant.now().plus(1, ChronoUnit.HOURS));
        addSomeEvents(List.of("Event3"));

        Map<Instant, List<String>> stat = eventsStatistic.getAllEventStatistic();
        assertThat(stat)
                .containsEntry(clock.instant(), List.of("Event3"));
    }

    private void addSomeEvents(List<String> events) {
        events.forEach(eventsStatistic::incEvent);
    }
}