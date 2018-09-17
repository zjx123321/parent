package com.zjx.demo.factory.d;

import com.zjx.demo.factory.c.IMobile;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public class MobileFactory implements IFactory<IMobile> {

    private MobileFactory(){}

    public static MobileFactory getInstance() {return new MobileFactory();}

    public <T extends IMobile> IMobile create(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        return tClass.newInstance();
    }
}
