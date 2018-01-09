package com.zjx.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhuenjun on 2017/12/19.
 */
public class TestZookeeper {
    private final ZooKeeper zk;
    private static final ArrayList<ACL> ACL = ZooDefs.Ids.OPEN_ACL_UNSAFE;

    public TestZookeeper() throws IOException {
        zk = new ZooKeeper("192.168.133.200:2181", 2000, null);
    }

    @Test
    public void testCreate() throws Exception {
         //zk.create("/teacher", "1".getBytes(), ACL, CreateMode.PERSISTENT);
       // zk.create("/teacher/test", "1".getBytes(), ACL, CreateMode.PERSISTENT);
       // zk.create("/teacher/PERSISTENT", "1".getBytes(), ACL, CreateMode.PERSISTENT);
       //  zk.create("/teacher/PERSISTENT_SEQUENTIAL", "1".getBytes(), ACL, CreateMode.PERSISTENT_SEQUENTIAL);
        zk.create("/teacher/EPHEMERAL", "1".getBytes(), ACL, CreateMode.EPHEMERAL);
       //  zk.create("/teacher/EPHEMERAL_SEQUENTIAL", "1".getBytes(), ACL, CreateMode.EPHEMERAL_SEQUENTIAL);
      //  zk.create("/teacher/test", "".getBytes(), ACL, CreateMode.PERSISTENT, new TestStringCallBack(), "context");
       while (true){}
    }

    class TestStringCallBack implements AsyncCallback.StringCallback {
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("callback-->" + rc + "," + path + "," + ctx + "," + name);
            //callback-->0,/teacher/yibu,context,/teacher/yibu0000000006
            //callback-->-110,/teacher/test,context,null
        }
    }

    @Test
    public void test(){
        Transaction transaction = zk.transaction();
        transaction.create("/teacher/tra","".getBytes(),ACL,CreateMode.PERSISTENT);

        transaction.delete("/teacher/tra",-1);
    }

    @Test
    public void testDel() throws Exception {
        zk.delete("/teacher/PERSISTENT", -1);
        //rg.apache.zookeeper.KeeperException$BadVersionException: KeeperErrorCode = BadVersion for /teacher/PERSISTENT
    }


    @Test
    public void testExists() throws Exception {
        Stat stat = zk.exists("/teacher/test", false);
        System.out.println(stat);
    }


    @Test
    public void testSet() throws Exception {
        Stat stat = zk.setData("/teacher/test", "3".getBytes(), -1);
        System.out.println(stat.toString());
    }

    @Test
    public void testGet() throws Exception {
        byte[] bytes = zk.getData("/teacher", false, null);
        String str = new String(bytes);
        System.out.println(str);

        List<String> childrens = zk.getChildren("/teacher", false);
        System.out.println(childrens.toString());
    }


    @Test
    public void testGetDataWatch() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        byte[] bytes = zk.getData("/teacher/test", new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.toString());
              try {
                   byte[] bytes = zk.getData("/teacher/test", this, null);
                  String str = new String(bytes);
                 System.out.println(str);
              } catch (Exception e) {
                  e.printStackTrace();
              }

            }
        }, null);
        String str = new String(bytes);
        System.out.println(str);
        countDownLatch.await();
    }

    @Test
    public void testExistsWatcher() throws Exception {
        Stat stat = zk.exists("/teacher/test", new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event);
                try {
                    Stat statWatch = zk.exists("/teacher/test", this);
                    System.out.println(statWatch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(stat);
        while (true) {
        }
    }

    @Test
    public void testGetChildrenWatch() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        List<String> childrens = zk.getChildren("/teacher", new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event);
                if (event.getType() == Event.EventType.NodeChildrenChanged) {
                    System.out.println("节点信息改变");
                }
                try {
                    List<String> childrens = zk.getChildren("/teacher", this);
                    System.out.println(childrens);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //org.apache.zookeeper.KeeperException$NoNodeException: KeeperErrorCode = NoNode for /teacher
        System.out.println(childrens);

        countDownLatch.await();
    }
}
