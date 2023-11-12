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

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin
@RestController
public class VisitorAnalyticsController {

    @Autowired
    private EventService eventService;
    @Autowired
    private RevenueService revenueService;

    @Operation(summary = "Get all registered events")
    @GetMapping("events")
    public Collection<EventData> getEvents() {
        return eventService.getEvents();
    }

    @Operation(summary = "Get a specific event by it's name")
    @GetMapping("events/{name}")
    public EventData getEvent(@PathVariable("name") String name) {
        return eventService.getEvent(name);
    }

    @Operation(summary = "Get all revenue data between two specific dates")
    @GetMapping("revenue")
    public Collection<RevenueData> getRevenueData(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {
        List<LocalDate> allDates = DateUtil.getDatesBetween(from, to);
        return allDates.stream().map(date -> revenueService.getRevenue(date))
                .filter(revenues -> !revenues.isEmpty())
                .map(revenues -> revenues.get(0))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get revenue score between two specific dates")
    @GetMapping("revenue-score")
    public Collection<RevenueData> getRevenueScoreData(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {
        List<LocalDate> allDates = DateUtil.getDatesBetween(from, to);
        return allDates.stream().map(date -> revenueService.getRevenueScore(date)).toList();
    }

    @Operation(summary = "Get all payment categories")
    @GetMapping("revenue/industries")
    public Collection<String> getPaymentCategories() {
        return revenueService.getIndustries();
    }

    @Operation(summary = "Get revenue score between two dates for a specific industry")
    @GetMapping("revenue-score/industry")
    public Collection<RevenueData> getRevenueScoreByIndustry(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to, @RequestParam String industry) {
        List<LocalDate> allDates = DateUtil.getDatesBetween(from, to);
        return allDates.stream().map(date -> revenueService.getRevenueScore(date, industry)).toList();
    }
}
