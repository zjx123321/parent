package com.zjx.demo.factory.c;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class XiaomiNote3Factory implements IFactory {
    public IMobile create() {
        return new XiaomiNote3Mobile();
    }
}
