package com.tff.salzburg.visitoranalytics.repository;

import java.util.Date;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ElasticsearchRepository<EventEntry, Date> {

    EventEntry findEventByName(String name);
}
