package com.tff.salzburg.visitoranalytics.service;

import com.tff.salzburg.visitoranalytics.DateUtil;
import com.tff.salzburg.visitoranalytics.controller.EventData;
import com.tff.salzburg.visitoranalytics.controller.RevenueData;
import com.tff.salzburg.visitoranalytics.repository.EventEntry;
import com.tff.salzburg.visitoranalytics.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventService {

    private int daysBeforeEvent = 5;
    private int daysAfterEvent = 5;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RevenueService revenueService;

    public Collection<EventData> getEvents() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                .map(this::entryToEventData)
                .collect(Collectors.toList());
    }

    public EventData getEvent(String name) {
        EventEntry entry = eventRepository.findEventByName(name);
        return entryToEventData(entry);
    }

    private EventData entryToEventData(EventEntry entry) {
        LocalDate localDate = entry.getTimestamp();
        LocalDate start = localDate.minusDays(daysBeforeEvent);
        LocalDate end = localDate.plusDays(daysAfterEvent);
        Integer revenue = (int) DateUtil.getDatesBetween(start, end).stream()
                .flatMap(date -> revenueService.getRevenue(date).stream())
                .mapToDouble(RevenueData::getRevenue)
                .average()
                .orElse(0);
        long visitors = eventRepository.findEventByName(entry.getName()).getArrivingVisitors();
        return new EventData(entry.getName(), entry.getDisplayName(), start, end, entry.getTimestamp(),
                revenue, visitors);
    }
}
