package com.zjx.demo;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhengjiexiang on 2018/1/3
 */
public class KafkaConsumer {

    private final ConsumerConnector consumer;

    private KafkaConsumer() {
        Properties props = new Properties();

        // zookeeper 配置
        props.put("zookeeper.connect", "192.168.133.200:2181");

        // 消费者所在组
        props.put("group.id", "testgroup");

        // zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");

        // 序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        ConsumerConfig config = new ConsumerConfig(props);

        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
    }

    void consume() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(KafkaProperties.TOPIC, new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap =
                consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(KafkaProperties.TOPIC).get(0);
        ConsumerIterator<String, String> it = stream.iterator();

        int messageCount = 0;
        while (it.hasNext()){
            System.out.println(it.next().message());
            messageCount++;
            if(messageCount == 100){
                System.out.println("Consumer端一共消费了" + messageCount + "条消息！");
            }
        }
    }

    public static void main(String[] args) {
        new KafkaConsumer().consume();
    }

}
