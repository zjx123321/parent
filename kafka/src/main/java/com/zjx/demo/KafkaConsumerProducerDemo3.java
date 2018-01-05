package com.zjx.demo;

public class KafkaConsumerProducerDemo3 {
    public static void main(String[] args) {
//        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
//        Producer producerThread = new Producer(KafkaProperties.TOPIC, true);
//        producerThread.start();
//
//        Producer producerThread2 = new Producer(KafkaProperties.TOPIC, true);
//        producerThread2.start();

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}