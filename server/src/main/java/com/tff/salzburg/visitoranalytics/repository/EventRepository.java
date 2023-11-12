package com.tff.salzburg.visitoranalytics.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EventRepository extends ElasticsearchRepository<EventEntry, Date> {

    EventEntry findEventByName(String name);
}
