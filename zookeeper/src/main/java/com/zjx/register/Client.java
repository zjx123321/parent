package com.zjx.register;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjiexiang on 2018/1/9
 */
public class Client implements Notify{

    private List<String> loadBalanceList = new ArrayList<String>();

    private Integer index = 0;

    private Integer size = 0;

    private Integer current = 0;

    public Client(Integer index) {
        this.index = index;
        init();
    }

    private void init() {
        loadBalanceList = RegisterCenter.getChildPath();
        size = loadBalanceList.size();
        RegisterCenter.addNotify(this);
    }

    public void notifyUpload() {
        init();
    }

    public void test() {
        String data = RegisterCenter.getData(loadBalanceList.get((current++)%size));
        System.out.println("客户端 " + index + " 得到 ：" + data);
    }
}
