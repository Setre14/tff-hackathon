package com.tff.salzburg.visitoranalytics;

import java.util.Date;

public class MastercardEntry {

    private final Date date;
    private final String industry;
    private final double txnAmount;

    public MastercardEntry(Date date, String industry, double txnAmount) {
        this.date = date;
        this.industry = industry;
        this.txnAmount = txnAmount;
    }
}
