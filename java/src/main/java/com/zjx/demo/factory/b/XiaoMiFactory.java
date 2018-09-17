package com.zjx.demo.factory.b;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class XiaoMiFactory implements IFactory {
    public IMobile create() {
        return new XiaoMiMobile();
    }
}
