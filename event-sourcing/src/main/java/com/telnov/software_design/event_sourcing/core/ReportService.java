package com.telnov.software_design.event_sourcing.core;

import java.time.LocalDate;
import java.util.List;

import com.telnov.software_design.event_sourcing.model.Event;
import com.telnov.software_design.event_sourcing.model.EventListener;
import com.telnov.software_design.event_sourcing.model.StatInfo;
import com.telnov.software_design.event_sourcing.model.UserEntryEvent;
import com.telnov.software_design.event_sourcing.storage.LocalStorage;
import org.springframework.stereotype.Component;

@Component
public class ReportService implements EventListener {

    private final LocalStorage localStorage;

    public ReportService(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    @Override
    public void handleEvent(long accountId, Event event) {
        if (event instanceof UserEntryEvent) {
            localStorage.incDate(((UserEntryEvent) event).getEntryDateTime().toLocalDate());
        }
    }

    public List<StatInfo> getDailyStat(LocalDate from, LocalDate to) {
        return localStorage.get(from, to);
    }
}
