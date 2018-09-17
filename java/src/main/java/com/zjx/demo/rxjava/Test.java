package com.zjx.demo.rxjava;

/**
 * Created by zhengjiexiang on 2018/2/12
 */
public class Test {

    public static void main(String[] args) {
        /**
         * test1
         */
        Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Integer>() {
            public void onStart() {
                System.out.println("开始执行");
            }
            public void onCompleted() {
                System.out.println("执行结束");
            }
            public void onError(Throwable t) {

            }
            public void onNext(Integer var1) {
                System.out.println("处理了事件 " + var1);
            }
        });

        /**
         * test2
         */
        Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).map(new Observable.Transformer<Integer, String>() {
            public String call(Integer from) {
                return "maping " + from;
            }
        }).subscribe(new Subscriber<String>() {
            public void onNext(String var1) {
                System.out.println(var1);
            }
            public void onCompleted() {}
            public void onError(Throwable t) {}
        });
    }

}
