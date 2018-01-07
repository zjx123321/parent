package com.zjx.partition;

import com.zjx.demo.KafkaProperties;

public class KafkaConsumerDemo22 {
    public static void main(String[] args) throws InterruptedException {

        Consumer2 consumerThread = new Consumer2(KafkaProperties.TOPIC2);
        consumerThread.start();

    }
}