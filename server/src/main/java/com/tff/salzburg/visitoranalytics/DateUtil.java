package com.tff.salzburg.visitoranalytics;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateUtil {

    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static List<Date> getDatesBetween(Date startDate, Date endDate) {
        return DateUtil.dateToLocalDate(startDate)
                .datesUntil(DateUtil.dateToLocalDate(endDate))
                .map(localDate -> DateUtil.localDateToDate(localDate))
                .collect(Collectors.toList());
    }
}
