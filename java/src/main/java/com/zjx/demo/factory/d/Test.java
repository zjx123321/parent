package com.zjx.demo.factory.d;


import com.zjx.demo.factory.c.*;
import com.zjx.demo.factory.c.IFactory;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class Test {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        IMobile mix2 = MobileFactory.getInstance().create(XiaomiX2Mobile.class);
        mix2.run();

        IMobile note3 = MobileFactory.getInstance().create(XiaomiNote3Mobile.class);
        note3.run();

    }

}
