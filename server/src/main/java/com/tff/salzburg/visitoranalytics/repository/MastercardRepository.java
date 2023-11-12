package com.tff.salzburg.visitoranalytics.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MastercardRepository extends ElasticsearchRepository<MastercardEntry, LocalDate> {

    Page<MastercardEntry> findByTimestamp(LocalDate name, Pageable pageable);

}
