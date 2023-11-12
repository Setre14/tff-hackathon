package com.tff.salzburg.visitoranalytics.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "mastercard-all-transactions")
public class MastercardEntry {

    @Id
    @Field(name = "@timestamp", type = FieldType.Date, format = DateFormat.date)
    private LocalDate timestamp;
    private final String industry;
    @Field(name = "txn_amt")
    private final Double txnAmount;

    @Field(name = "quad_id")
    private final Long quadId;

    public MastercardEntry(LocalDate timestamp, String industry, Double txnAmount, Long quadId) {
        this.timestamp = timestamp;
        this.industry = industry;
        this.txnAmount = txnAmount;
        this.quadId = quadId;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getIndustry() {
        return industry;
    }

    public Double getTxnAmount() {
        return txnAmount;
    }

    public Long getQuadId() {
        return quadId;
    }
}
