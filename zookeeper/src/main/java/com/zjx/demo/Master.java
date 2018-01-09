package com.zjx.demo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;

/**
 * Created by cto on 2017/12/19.
 * <p>
 * master
 */
public class Master {
    private static final String ROOTPATH = "/standby";

    private final ZooKeeper zk;
    private boolean master = false;
    private static final ArrayList<org.apache.zookeeper.data.ACL> ACL = ZooDefs.Ids.OPEN_ACL_UNSAFE;

    public Master(ZooKeeper zooKeeper) {
        this.zk = zooKeeper;
    }

    public boolean isMaster() {
        return master;
    }

    public Master setMaster() {
        try {
            Thread.sleep(500);
            zk.create(ROOTPATH, "".getBytes(), ACL, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            return this;
        }
        this.master = true;
        return this;
    }

    public void outPut() throws Exception {
        while (true) {
            System.out.println("hello,world");
            Thread.sleep(2000L);
        }

    }
}
