package com.tff.salzburg.visitoranalytics.controller;

import java.time.LocalDate;

public class EventData extends SimpleEventData {

    private final LocalDate start;
    private final LocalDate end;

    public EventData(String name, String displayName, LocalDate start, LocalDate end, LocalDate eventDate, Integer revenue, long visitors) {
        super(name, displayName, eventDate, revenue, visitors);
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
