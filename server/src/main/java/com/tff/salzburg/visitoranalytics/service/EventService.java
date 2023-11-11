package com.tff.salzburg.visitoranalytics.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tff.salzburg.visitoranalytics.DateUtil;
import com.tff.salzburg.visitoranalytics.controller.EventData;
import com.tff.salzburg.visitoranalytics.controller.RevenueData;
import com.tff.salzburg.visitoranalytics.controller.SimpleEventData;
import com.tff.salzburg.visitoranalytics.repository.EventEntry;
import com.tff.salzburg.visitoranalytics.repository.EventRepository;

@Service
public class EventService {

    private int daysBeforeEvent = 5;
    private int daysAfterEvent = 5;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RevenueService revenueService;

    public Collection<SimpleEventData> getEvents() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                .map(this::entryToEventData)
                .collect(Collectors.toList());
    }

    public EventData getEvent(String name) {
        EventEntry entry = eventRepository.findEventByName(name);
        return entryToEventData(entry);
    }

    private EventData entryToEventData(EventEntry entry) {
        LocalDate localDate = DateUtil.dateToLocalDate(entry.getTimestamp());
        Date start = DateUtil.localDateToDate(localDate.minusDays(daysBeforeEvent));
        Date end = DateUtil.localDateToDate(localDate.plusDays(daysAfterEvent));
        Double revenue = DateUtil.getDatesBetween(start, end).stream()
                .flatMap(date -> revenueService.getRevenue(date).stream())
                .mapToDouble(RevenueData::getRevenue)
                .sum();
        long visitors = 0; // TODO
        return new EventData(entry.getName(), entry.getDisplayName(), start, end, entry.getTimestamp(),
                revenue, visitors);
    }
}