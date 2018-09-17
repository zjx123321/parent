package com.zjx.demo.factory.a;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class Test {

    public static void main(String[] args) {
        Mobile mobile = MobileFactory.create();
        mobile.hello();
    }

}
