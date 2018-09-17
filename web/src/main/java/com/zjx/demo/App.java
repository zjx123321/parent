package com.zjx.demo;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.ScheduledReporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App
{
    public static void main( String[] args )
    {
        ApplicationContext ctx = SpringApplication.run(App.class, args);

//         启动Reporter
        ConsoleReporter reporter = ctx.getBean(ConsoleReporter.class);
        reporter.start(60, TimeUnit.SECONDS);

        ScheduledReporter scheduledReporter = (ScheduledReporter) ctx.getBean("influxdbReporter");
        scheduledReporter.start(60, TimeUnit.SECONDS);

    }
}
