package com.zjx.register;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zhengjiexiang on 2018/1/9
 */
public class TestClient3 {

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    public static void main(String[] args) throws Exception{
        Client client1 = new Client(2);
        while(true) {
            Thread.sleep(2000);
            client1.test();
        }

    }

}
