package com.zjx.partition;

import com.zjx.demo.KafkaProperties;

public class KafkaConsumerDemo2 {
    public static void main(String[] args) throws InterruptedException {

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}