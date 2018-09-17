package com.zjx.demo.proxy;

/**
 * Created by zhengjiexiang on 2018/4/9
 */
public class Test {

    public static void main(String[] args) {
        Login login = new LoginProxy();
        login.login();
    }

}
