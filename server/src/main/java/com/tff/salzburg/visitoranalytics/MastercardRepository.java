package com.tff.salzburg.visitoranalytics;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MastercardRepository {

    Page<MastercardEntry> findByDate(Date name, Pageable pageable);

}
