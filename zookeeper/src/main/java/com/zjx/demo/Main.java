package com.zjx.demo;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by cto on 2017/12/19.
 * 一个世界上最小化的主备高可用项目,重在思想,不要在意细节
 */
public class Main {
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);


    public static void main(String[] args) throws Exception {
        final ZooKeeper zooKeeper = new ZooKeeper("192.168.133.200:2181", 5000, null);
        Master master = new Master(zooKeeper);
        if (master.setMaster().isMaster()) {
            master.outPut();
        }
        new MasterStandby(zooKeeper).monitor();
        COUNT_DOWN_LATCH.await();
    }

}
