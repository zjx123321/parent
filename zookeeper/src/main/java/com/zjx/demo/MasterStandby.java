package com.zjx.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by cto on 2017/12/19.
 * <p>
 * MasterStandby master备用
 */
public class MasterStandby implements Watcher {
    private final ZooKeeper zk;
    private static final String ROOTPATH = "/standby";

    public MasterStandby(ZooKeeper zooKeeper) {
        this.zk = zooKeeper;
    }

    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDeleted) {
            System.out.println("节点挂了,启动备用节点");
            Master master = new Master(zk);
            if (master.setMaster().isMaster()) {
                try {
                    master.outPut();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                zk.exists(ROOTPATH, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void monitor() throws Exception {
        zk.exists(ROOTPATH, this);
    }
}
