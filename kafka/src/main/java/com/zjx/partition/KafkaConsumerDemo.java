package com.zjx.partition;

import com.zjx.demo.KafkaProperties;

public class KafkaConsumerDemo {
    public static void main(String[] args) throws InterruptedException {

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}