package com.zjx.demo.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengjiexiang on 2018/4/8
 */
public class OuterUserImpl implements OuterUser {
    public Map<String, String> getUserInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zjx");
        map.put("age", "27");
        map.put("address", "suzhou");
        return map;
    }
}
