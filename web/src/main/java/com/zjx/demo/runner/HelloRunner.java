package com.zjx.demo.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by zhengjiexiang on 2019/2/2
 */
@Component
public class HelloRunner implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("hello runner");
    }
}
