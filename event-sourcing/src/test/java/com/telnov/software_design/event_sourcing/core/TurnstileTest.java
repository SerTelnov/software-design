package com.telnov.software_design.event_sourcing.core;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.telnov.software_design.event_sourcing.model.AccountExtensionEvent;
import com.telnov.software_design.event_sourcing.model.UserEntryEvent;
import com.telnov.software_design.event_sourcing.model.UserEscapeEvent;
import com.telnov.software_design.event_sourcing.storage.EventStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TurnstileTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2020, 4, 6, 13, 40);
    private static final LocalDate LOCAL_DATE = LOCAL_DATE_TIME.toLocalDate();

    @Mock
    private EventStorage eventStorageMock;
    private Clock fixedClock = Clock.fixed(Instant.parse("2020-04-06T10:40:00Z"), ZoneId.systemDefault());

    private Turnstile turnstile;

    @BeforeEach
    void setup() {
        this.turnstile = new Turnstile(eventStorageMock, fixedClock);
    }

    @Test
    void testEntry() {
        Mockito.when(eventStorageMock.get(eq(1L)))
                .thenReturn(List.of(new AccountExtensionEvent(LOCAL_DATE, 7)));

        turnstile.entry(1L);
        Mockito.verify(eventStorageMock, times(1))
                .save(eq(1L), eq(new UserEntryEvent(LOCAL_DATE_TIME)));
    }

    @Test
    void testEscape() {
        turnstile.escape(1L);
        Mockito.verify(eventStorageMock, times(1))
                .save(eq(1L), eq(new UserEscapeEvent(LOCAL_DATE_TIME)));
    }

    @Test
    void testCantEntry() {
        Mockito.when(eventStorageMock.get(eq(1L)))
                .thenReturn(List.of(new AccountExtensionEvent(LOCAL_DATE.minusDays(10), 1)));

        Assertions.assertThrows(
                RuntimeException.class,
                () -> turnstile.entry(1L)
        );
    }
}
