package com.zjx.demo.factory.c;

/**
 * Created by zhengjiexiang on 2018/4/4
 */
public abstract class AbstractMobile implements IMobile{

    public void run() {
        logo();
        call();
        pic();
    }

    abstract void logo();

    abstract void call();

    abstract void pic();

}
