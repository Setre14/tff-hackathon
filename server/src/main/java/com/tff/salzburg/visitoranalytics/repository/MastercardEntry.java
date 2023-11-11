package com.tff.salzburg.visitoranalytics.repository;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "mastercard")
public class MastercardEntry {

    @Id
    @Field(name = "@timestamp", type = FieldType.Date)
    private Date timestamp;
    private final String industry;
    @Field(name = "txn_amt")
    private final Double txnAmount;

    public MastercardEntry(Date timestamp, String industry, Double txnAmount) {
        this.timestamp = timestamp;
        this.industry = industry;
        this.txnAmount = txnAmount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getIndustry() {
        return industry;
    }

    public Double getTxnAmount() {
        return txnAmount;
    }
}
