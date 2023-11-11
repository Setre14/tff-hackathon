package com.tff.salzburg.visitoranalytics.controller;

import java.util.Date;

public class EventData extends SimpleEventData {

    private final Date start;
    private final Date end;

    public EventData(String name, String displayName, Date start, Date end, Date eventDate, Double revenue, long visitors) {
        super(name, displayName, eventDate, revenue, visitors);
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
