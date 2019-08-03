package com.zjx.demo.controller;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by zhengjiexiang on 2017/12/22
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String sayHello(@RequestParam("name") String name) {
        logger.info("请求参数name:{}", name);
        return "Hello" + name;
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String sayHi(@RequestParam("name") String name) {
        logger.info("请求参数name:{}", name);
        return "Hello" + name;
    }

    @RequestMapping(value = "/yes", method = RequestMethod.GET)
    public String sayYes(@RequestParam("name") String name) {
        logger.info("请求参数name:{}", name);
        return "Hello" + name;
    }


    private static Random random = new Random();

    private static final Counter requestTotal = Counter.build()
            .name("my_sample_counter")
            .labelNames("status")
            .help("A simple Counter to illustrate custom Counters in Spring Boot and Prometheus").register();

    @RequestMapping("/endpoint")
    public void endpoint() {
        if (random.nextInt(2) > 0) {
            requestTotal.labels("success").inc();
        } else {
            requestTotal.labels("error").inc();
        }
    }

}
