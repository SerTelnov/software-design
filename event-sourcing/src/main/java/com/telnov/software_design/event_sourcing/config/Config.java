package com.telnov.software_design.event_sourcing.config;

import java.time.Clock;

import com.telnov.software_design.event_sourcing.core.ReportService;
import com.telnov.software_design.event_sourcing.model.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    private ReportService reportService;

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public EventHandler eventHandler() {
        final var handler = new EventHandler();
        handler.addListener(reportService);
        return handler;
    }
}
