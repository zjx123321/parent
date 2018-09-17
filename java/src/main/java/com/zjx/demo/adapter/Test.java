package com.zjx.demo.adapter;

/**
 * Created by zhengjiexiang on 2018/4/8
 */
public class Test {

    public static void main(String[] args) {
        User user = new UserAdapter();
        System.out.println(user.getName());
        System.out.println(user.getAge());
        System.out.println(user.getAddress());
    }

}
