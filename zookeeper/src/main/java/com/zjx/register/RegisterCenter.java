package com.zjx.register;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjiexiang on 2018/1/9
 */
public class RegisterCenter {

    private static ZooKeeper zooKeeper;

    private static ArrayList<org.apache.zookeeper.data.ACL> ACL = ZooDefs.Ids.OPEN_ACL_UNSAFE;

    private static List<Notify> allNotifys = new ArrayList<Notify>();

    static{
        try {
            zooKeeper = new ZooKeeper("192.168.133.200:2181", 5000, null);
            zooKeeper.create("/services", "".getBytes(), ACL, CreateMode.PERSISTENT);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(String serviceName) {
        try {
            zooKeeper.create("/services/test", serviceName.getBytes(), ACL, CreateMode.EPHEMERAL_SEQUENTIAL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addNotify(Notify notify) {
        allNotifys.add(notify);
    }

    public static List<String> getChildPath() {
        try {
            List<String> childrens = zooKeeper.getChildren("/services", new Watcher() {
                public void process(WatchedEvent event) {
                    System.out.println(event);
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        allNotifys.forEach(n -> n.notifyUpload());
                    }
//                    try {
//                        List<String> childrens = zooKeeper.getChildren("/services", this);
//                        System.out.println(childrens);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            });
            return childrens;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public static String getData(String path) {
        try {
            byte[] bytes = zooKeeper.getData("/services/" + path, false, null);
            return new String(bytes);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
