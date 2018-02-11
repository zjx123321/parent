package com.zjx.demo.config;

import com.codahale.metrics.*;
import metrics_influxdb.InfluxdbReporter;
import metrics_influxdb.api.protocols.InfluxdbProtocols;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class MetricConfig {

    @Bean
    public MetricRegistry metrics() {
        return new MetricRegistry();
    }

    /**
     * Reporter 数据的展现位置
     *
     * @param metrics
     * @return
     */
    @Bean
    public ConsoleReporter consoleReporter(MetricRegistry metrics) {
        return ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
    }

    @Bean
    public Slf4jReporter slf4jReporter(MetricRegistry metrics) {
        return Slf4jReporter.forRegistry(metrics)
                .outputTo(LoggerFactory.getLogger("demo.metrics"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
    }

    @Bean
    public JmxReporter jmxReporter(MetricRegistry metrics) {
        return JmxReporter.forRegistry(metrics).build();
    }

    @Bean(name = "influxdbReporter")
    public ScheduledReporter influxdbReporter(MetricRegistry metrics) throws Exception {

        return InfluxdbReporter.forRegistry(metrics)
                .protocol(InfluxdbProtocols.http("192.168.133.200", 8086, "admin", "admin", "zjxtest"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .skipIdleMetrics(false)
                .build();
    }

    /**
     * 工厂类
     *
     * @return
     */
    @Bean
    public MeterFactory meterFactory() {
        return new MeterFactory();
    }

    /**
     * 直方图
     *
     * @param metrics
     * @return
     */
    @Bean
    public Histogram responseSizes(MetricRegistry metrics) {
        return metrics.histogram("response-sizes");
    }

//    /**
//     * 计数器
//     *
//     * @param metrics
//     * @return
//     */
//    @Bean
//    public Counter pendingJobs(MetricRegistry metrics) {
//        return metrics.counter("requestCount");
//    }

    /**
     * 工厂类
     * @return
     */
    @Bean
    public TimerFactory timerFactory() {
        return new TimerFactory();
    }

    /**
     * 工厂类
     * @return
     */
    @Bean
    public CounterFactory counterFactory() {
        return new CounterFactory();
    }

    public class MeterFactory {
        @Resource
        private MetricRegistry metricRegistry;

        private Map<String, Meter> meterMap = new HashMap<>();

        public Meter getMeter(String name) {
            if(meterMap.get(name) == null) {
                Meter meter = metricRegistry.meter(name);
                meterMap.put(name, meter);
                return meter;
            }
            return meterMap.get(name);
        }
    }

    public class CounterFactory {
        @Resource
        private MetricRegistry metricRegistry;

        private Map<String, Counter> counterMap = new HashMap<>();

        public Counter getCounter(String name) {
            if(counterMap.get(name) == null) {
                Counter meter = metricRegistry.counter(name);
                counterMap.put(name, meter);
                return meter;
            }
            return counterMap.get(name);
        }
    }

    public class TimerFactory {
        @Resource
        private MetricRegistry metricRegistry;

        private Map<String, Timer> timerMap = new HashMap<>();

        public Timer getTimer(String name) {
            if(timerMap.get(name) == null) {
                Timer timer = metricRegistry.timer(name);
                timerMap.put(name, timer);
                return timer;
            }
            return timerMap.get(name);
        }
    }

}
