package com.tff.salzburg.visitoranalytics.service;

import com.tff.salzburg.visitoranalytics.controller.RevenueData;
import com.tff.salzburg.visitoranalytics.repository.MastercardEntry;
import com.tff.salzburg.visitoranalytics.repository.MastercardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class RevenueService {

    @Autowired
    private MastercardRepository mastercardRepository;

    public List<RevenueData> getRevenue(LocalDate date) {
        Stream<MastercardEntry> entries = mastercardRepository.findByTimestamp(date, Pageable.unpaged()).get();
        return entries.map(e -> new RevenueData(e.getTxnAmount(), e.getTimestamp())).collect(Collectors.toList());
    }

    public RevenueData getRevenueScore(LocalDate date) {
        Stream<MastercardEntry> entries = mastercardRepository.findByTimestamp(date, Pageable.unpaged()).get();
        Double average = entries.mapToDouble(MastercardEntry::getTxnAmount).average().orElse(-1);
        return new RevenueData(average, date);
    }

    public RevenueData getRevenueScore(LocalDate date, String industry) {
        Stream<MastercardEntry> entries = mastercardRepository.findByTimestampAndIndustry(date, industry, Pageable.unpaged()).get();
        Double average = entries.mapToDouble(MastercardEntry::getTxnAmount).average().orElse(-1);
        return new RevenueData(average, date);
    }

    public List<String> getIndustries() {
        return StreamSupport.stream(mastercardRepository.findAll().spliterator(), false)
                .map(MastercardEntry::getIndustry)
                    .distinct()
                .toList();
    }
}
