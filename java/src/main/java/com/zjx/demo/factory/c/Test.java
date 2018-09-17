package com.zjx.demo.factory.c;



/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class Test {

    public static void main(String[] args) {
        IFactory mix2f = new XiaomiX2Factory();
        IMobile mix2 = mix2f.create();
        mix2.run();

        IFactory note3f = new XiaomiNote3Factory();
        IMobile note3 = note3f.create();
        note3.run();

    }

}
