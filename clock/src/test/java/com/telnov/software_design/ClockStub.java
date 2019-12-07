package com.telnov.software_design;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockStub extends Clock {

    private Instant now;

    public ClockStub(Instant now) {
        this.now = now;
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instant instant() {
        return now;
    }

    public void updNow(Instant now) {
        this.now = now;
    }
}
