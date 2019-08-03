package com.zjx.demo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by zhengjiexiang on 2019/1/29
 */
public class Test {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "10");
        map.put("2", "4");
        map.put("3", "12");
        Map<String, String> map2 = new LinkedHashMap<>();


        Stream<Map.Entry<String, String>> sorted = map.entrySet().stream().sorted(Comparator.comparing(e -> Integer.valueOf(e.getValue())));
        sorted.forEach(e -> map2.put(e.getKey(), e.getValue()));

        System.out.println(map2);
    }

}
