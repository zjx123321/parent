package com.zjx.demo.factory.c;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class XiaomiX2Factory implements IFactory {
    public IMobile create() {
        return new XiaomiX2Mobile();
    }
}
