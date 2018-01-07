package com.zjx.partition.algorithm;

import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 
 * @author Lijie
 *
 */
public class MyProducer {

    public static void main(String[] args) throws Exception {
        produce();
    }

    public static void produce() throws Exception {

        //topic
        String topic = "mytopic";

        //配置
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.52.200:9092");

        //序列化类型
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //创建生产者
        KafkaProducer<String, String> pro = new KafkaProducer(properties);
        while (true) {

            //模拟message
            String value = UUID.randomUUID().toString();

            //封装message
            ProducerRecord<String, String> pr = new ProducerRecord<String, String>(topic, value);

            //发送消息
            pro.send(pr);

            //sleep
            Thread.sleep(1000);
        }
    }
}