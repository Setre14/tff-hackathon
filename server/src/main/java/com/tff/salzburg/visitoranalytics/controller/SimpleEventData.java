package com.tff.salzburg.visitoranalytics.controller;

import java.util.Calendar;
import java.util.Date;

public class SimpleEventData {

    private final String name;
    private final String displayName;
    private final Date eventDate;
    private final String weekday;
    private final Double revenue;
    private final long visitors;

    public SimpleEventData(String name, String displayName, Date eventDate, Double revenue, long visitors) {
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

    public Date getEventDate() {
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

    public static String getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        return switch (weekday) {
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            case 7 -> "Saturday";
            default -> throw new IllegalStateException("Unknown weekday for " + date);
        };
    }
}
