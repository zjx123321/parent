package com.zjx.demo;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@EnablePrometheusEndpoint
//@EnableSpringBootMetricsCollector
public class App
{

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> {
            System.out.println("500");
            return 500;
        };
    }

    @Bean
    public DisposableBean disposableBean() {
        return () -> {
            System.out.println("deploy");
        };
    }

    public static void main( String[] args )
    {
//        System.exit(SpringApplication
//                .exit(SpringApplicatin.run(App.class, args)));

        SpringApplication.run(App.class, args);

//         启动Reporter
//        ConsoleReporter reporter = ctx.getBean(ConsoleReporter.class);
//        reporter.start(60, TimeUnit.SECONDS);
//
//        ScheduledReporter scheduledReporter = (ScheduledReporter) ctx.getBean("influxdbReporter");
//        scheduledReporter.start(60, TimeUnit.SECONDS);

    }
}
