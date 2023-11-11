package com.tff.salzburg.visitoranalytics.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MastercardRepository extends ElasticsearchRepository<MastercardEntry, Date> {

    Page<MastercardEntry> findByTimestamp(Date name, Pageable pageable);

}
