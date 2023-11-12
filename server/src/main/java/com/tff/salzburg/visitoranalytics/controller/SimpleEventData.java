package com.tff.salzburg.visitoranalytics.controller;

import java.time.LocalDate;
import java.util.Calendar;

public class SimpleEventData {

    private final String name;
    private final String displayName;
    private final LocalDate eventDate;
    private final String weekday;
    private final Double revenue;
    private final long visitors;

    public SimpleEventData(String name, String displayName, LocalDate eventDate, Double revenue, long visitors) {
        this.name = name;
        this.displayName = displayName;
        this.eventDate = eventDate;
        this.weekday = getDayNumberOld(eventDate);
        this.revenue = revenue;
        this.visitors = visitors;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getWeekday() {
        return weekday;
    }

    public Double getRevenue() {
        return revenue;
    }

    public long getVisitors() {
        return visitors;
    }

    public static String getDayNumberOld(LocalDate date) {
        Calendar cal = Calendar.getInstance();
        return date.getDayOfWeek().name();
    }
}
