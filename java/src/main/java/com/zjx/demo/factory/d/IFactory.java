package com.zjx.demo.factory.d;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public interface IFactory<V>{

    <T extends V> V create(Class<T> tClass) throws IllegalAccessException, InstantiationException;

}
