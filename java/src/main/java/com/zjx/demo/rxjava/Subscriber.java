package com.zjx.demo.rxjava;

/**
 * 订阅者/观察者
 * @param <T>
 */
public abstract class Subscriber<T> implements Observer<T> {
    public void onStart() {
    }
}