package com.tff.salzburg.visitoranalytics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitorAnalyticsController {

    @GetMapping
    public String helloWorld() {
        return "Hello Visitor Analytics";
    }
}
