package com.zjx.offset;

import com.zjx.demo.KafkaProperties;

public class KafkaConsumerDemo {
    public static void main(String[] args) throws InterruptedException {

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC2);
        consumerThread.start();

    }
}