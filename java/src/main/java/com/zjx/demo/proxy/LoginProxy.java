package com.zjx.demo.proxy;

/**
 * Created by zhengjiexiang on 2018/4/9
 */
public class LoginProxy implements Login {

    LoginService loginService = new LoginService();

    @Override
    public void login() {
        System.out.println("检查。。。");
        loginService.login();
        System.out.println("登录成功");
    }
}
