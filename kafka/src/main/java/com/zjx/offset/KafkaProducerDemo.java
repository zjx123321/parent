package com.zjx.offset;

import com.zjx.demo.KafkaProperties;


public class KafkaProducerDemo {
    public static void main(String[] args) throws InterruptedException {
        Producer producerThread = new Producer(KafkaProperties.TOPIC2, true, "rex");
        producerThread.start();

        Thread.sleep(1000 * 3);

    }
}