package com.zjx.demo.adapter;

import java.util.Map;

/**
 * Created by zhengjiexiang on 2018/4/8
 */
public class UserAdapter extends OuterUserImpl implements User {

    Map<String, String> map = super.getUserInfo();

    @Override
    public String getName() {
        return map.get("name");
    }

    @Override
    public String getAge() {
        return map.get("age");
    }

    @Override
    public String getAddress() {
        return map.get("address");
    }
}
