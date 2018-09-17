package com.zjx.demo.dynamicproxy;

import com.zjx.demo.proxy.Login;
import com.zjx.demo.proxy.LoginService;

import java.lang.reflect.InvocationHandler;

/**
 * Created by zhengjiexiang on 2018/4/10
 */
public class Test {

    public static void main(String[] args) {
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);
        Login login = new LoginService();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(login);
        Login loginProxy = (Login)invocationHandler.getProxy();
        loginProxy.login();
        //生成代理类
        ProxyGeneratorUtils.writeProxyClassToHardDisk("F:/$Proxy11.class");
    }

}
