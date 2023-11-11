package com.tff.salzburg.visitoranalytics.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tff.salzburg.visitoranalytics.DateUtil;
import com.tff.salzburg.visitoranalytics.service.EventService;
import com.tff.salzburg.visitoranalytics.service.RevenueService;

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
    public Collection<RevenueData> getRevenueData(@RequestParam("from") Date from, @RequestParam("to") Date to) {
        List<Date> allDates = DateUtil.getDatesBetween(from, to);
        return allDates.stream().map(date -> revenueService.getRevenue(date))
                .filter(revenues -> !revenues.isEmpty())
                .map(revenues -> revenues.get(0))
                .collect(Collectors.toList());
    }
}
