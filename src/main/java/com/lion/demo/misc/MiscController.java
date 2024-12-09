package com.lion.demo.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/misc")
public class MiscController {

    @Autowired
    private ApiService apiService;
    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private MetricService metricService;


    @GetMapping("/api")
    public String api() {
        String result = apiService.fetchData();
        return result;
    }

    @GetMapping("/port")
    public String port() {
        return "Server port: " + serverPort;
    }

    @GetMapping("/record-metrics")
    public String recordMetrics(){
        metricService.recordCustomMetrics();
        return "Custom Metrics Record";
    }
}
