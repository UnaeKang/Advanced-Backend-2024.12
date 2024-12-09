package com.lion.demo.misc;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

    @Autowired
    private MeterRegistry meterRegistry;

    public void recordCustomMetrics() {
        // Counter example
        meterRegistry.counter("costom.metric.counter", "tag", "example").increment();

        // Timer example
        meterRegistry.timer("costom.metric.timer", "tag", "example")
                .record(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}
