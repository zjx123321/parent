package com.zjx.demo.factory.b;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class Test {

    public static void main(String[] args) {
        IFactory factory = new XiaoMiFactory();
        IMobile mobile = factory.create();
        mobile.hello();
    }

}
