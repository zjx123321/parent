package com.zjx.demo.proxy;

/**
 * Created by zhengjiexiang on 2018/4/9
 */
public class LoginService implements Login {
    @Override
    public void login() {
        System.out.println("登录。。。");
    }
}
