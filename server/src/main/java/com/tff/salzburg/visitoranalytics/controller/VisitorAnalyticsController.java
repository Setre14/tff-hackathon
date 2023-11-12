package com.tff.salzburg.visitoranalytics.controller;

import com.tff.salzburg.visitoranalytics.DateUtil;
import com.tff.salzburg.visitoranalytics.service.EventService;
import com.tff.salzburg.visitoranalytics.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class VisitorAnalyticsController {

    @Autowired
    private EventService eventService;
    @Autowired
    private RevenueService revenueService;

    @GetMapping
    public String helloWorld() {
        return "Hello Visitor Analytics";
    }

    @GetMapping("events")
    public Collection<SimpleEventData> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("events/{name}")
    public EventData getEvent(@PathVariable("name") String name) {
        return eventService.getEvent(name);
    }

    @GetMapping("revenue")
    public Collection<RevenueData> getRevenueData(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {
        List<LocalDate> allDates = DateUtil.getDatesBetween(from, to);
        return allDates.stream().map(date -> revenueService.getRevenue(date))
                .filter(revenues -> !revenues.isEmpty())
                .map(revenues -> revenues.get(0))
                .collect(Collectors.toList());
    }
}
