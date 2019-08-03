package com.zjx.demo.utils;

import io.prometheus.client.Counter;
import org.springframework.stereotype.Component;

@Component
public class CustomMetric {

    static final Counter requests = Counter.build().name("my_request_total").help("Total request.")
            .labelNames("url", "method", "code").register();

    public void processRequest(String... method){
        requests.labels(method);
    }
}