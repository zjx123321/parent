package com.zjx.register;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zhengjiexiang on 2018/1/9
 */
public class TestServer {

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    public static void main(String[] args) throws Exception{
        new Server("Server A").register();
        new Server("Server B").register();
        new Server("Server C").register();
        COUNT_DOWN_LATCH.await();
    }

}
