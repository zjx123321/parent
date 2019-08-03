package com.zjx.demo.list;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhengjiexiang on 2019/4/10
 */
public class Test {

    public static void main(String[] args) throws ParseException {

//        List<String> list = new ArrayList<>();
//        List<String> list2 = list;
//        list2.add("123");
//        System.out.println(list.size());


        List<Zjx> list = new ArrayList<>();
        List<Zjx> list2 = list;
        list2.add(new Zjx());
        System.out.println(list.hashCode()  + "  " + list2.hashCode());

//        String a = "cc";
//        String b = a;
//        b = "dd";
//        System.out.println(a);

        String a = new String("cc");
        String b = a;
        b = new String("dd");
        System.out.println(a.hashCode() + "  " + b.hashCode());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date parse = simpleDateFormat.parse("2018-10-10 10:10:10.222");
        System.out.println(parse);

    }

}
