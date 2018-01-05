package com.zjx.demo;

public class KafkaConsumerProducerDemo {
    public static void main(String[] args) throws InterruptedException {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        Producer producerThread = new Producer(KafkaProperties.TOPIC, true, 0);
        producerThread.start();

        Producer producerThread2 = new Producer(KafkaProperties.TOPIC, true, 1);
        producerThread2.start();

        Thread.sleep(1000 * 3);
//
//        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
//        consumerThread.start();

    }
}