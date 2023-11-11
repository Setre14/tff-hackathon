package com.tff.salzburg.visitoranalytics.repository;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "events")
public class EventEntry {

    @Id
    @Field(name = "@timestamp", type = FieldType.Date)
    private Date timestamp;
    @Field(name = "KeyX")
    private final String name;
    @Field(name = "Name")
    private final String displayName;

    public EventEntry(Date timestamp, String name, String displayName) {
        this.timestamp = timestamp;
        this.name = name;
        this.displayName = displayName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
