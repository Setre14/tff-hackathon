package com.tff.salzburg.visitoranalytics.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tff.salzburg.visitoranalytics.controller.RevenueData;
import com.tff.salzburg.visitoranalytics.repository.MastercardEntry;
import com.tff.salzburg.visitoranalytics.repository.MastercardRepository;

@Service
public class RevenueService {

    @Autowired
    private MastercardRepository mastercardRepository;

    public List<RevenueData> getRevenue(Date date) {
        Stream<MastercardEntry> entries = mastercardRepository.findByTimestamp(date, Pageable.unpaged()).get();
        return entries.map(e -> new RevenueData(e.getTxnAmount(), e.getTimestamp())).collect(Collectors.toList());
    }
}
