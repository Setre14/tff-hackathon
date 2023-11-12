package com.tff.salzburg.visitoranalytics.controller;

import java.time.LocalDate;

public class RevenueData {
    private final Double revenue;
    private final LocalDate date;

    public RevenueData(Double revenue, LocalDate date) {
        this.revenue = revenue;
        this.date = date;
    }

    public Double getRevenue() {
        return revenue;
    }

    public LocalDate getDate() {
        return date;
    }
}
