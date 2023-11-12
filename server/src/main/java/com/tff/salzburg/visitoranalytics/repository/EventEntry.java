package com.tff.salzburg.visitoranalytics.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "events-final-event-summary")
public class EventEntry {

    @Id
    @Field(name = "@timestamp", type = FieldType.Date, format = DateFormat.date)
    private LocalDate timestamp;
    @Field(name = "KeyX")
    private final String name;
    @Field(name = "Name")
    private final String displayName;

    public EventEntry(LocalDate timestamp, String name, String displayName) {
        this.timestamp = timestamp;
        this.name = name;
        this.displayName = displayName;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
