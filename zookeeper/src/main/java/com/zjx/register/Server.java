package com.zjx.register;

/**
 * Created by zhengjiexiang on 2018/1/9
 */
public class Server {

    private String name;

    public Server(String name) {
        this.name = name;
    }

    public void register() {
        RegisterCenter.register(name);
    }
}
