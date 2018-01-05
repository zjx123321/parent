package com.zjx.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Created by zhengjiexiang on 2018/1/5
 */
public class MyPartition implements Partitioner {
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] bytes1, Cluster cluster) {

        int partitionSize = cluster.partitionCountForTopic(topic);
        int partitionNum = 0;
        try {
            partitionNum = Integer.parseInt((String) key);
        } catch (Exception e) {
            partitionNum = key.hashCode() ;
        }
        return Math.abs(partitionNum % partitionSize);

    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
