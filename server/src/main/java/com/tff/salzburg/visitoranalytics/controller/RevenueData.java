package com.tff.salzburg.visitoranalytics.controller;

import java.util.Date;

public class RevenueData {
    private final Double revenue;
    private final Date date;

    public RevenueData(Double revenue, Date date) {
        this.revenue = revenue;
        this.date = date;
    }

    public Double getRevenue() {
        return revenue;
    }

    public Date getDate() {
        return date;
    }
}
