package com.study.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Ticket extends Thread {
    private final short totalTicket = 5000;
//    private short sell = 0;  // 不带static，表明sell是各个线程对象各自拥有的

    //static变量也称作静态变量
    // 静态变量和非静态变量的区别是：静态变量被所有的对象所共享，在内存中只有一个副本，它当且仅当在类初次加载时会被初始化。
    // 而非静态变量是对象所拥有的，在创建对象的时候被初始化，存在多个副本，各个对象拥有的副本互不影响
    private static short sell = 0;

    public Ticket(String name) {
        this.setName(name);
    }

    public Ticket() {
        super();
    }

    @Override
    public void run() {
        while (sell < totalTicket) {
            sell += 1;
            System.out.println(Thread.currentThread().getName() + "售出1张票, 总共售出" + sell);
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    public static void main(String[] args) {
//        for (short i = 0; i < 4; i++) {
//            new Ticket().start();
//        }
        IntStream.range(0, 4).boxed().map( i -> new Ticket("customsThread-" + i)).forEach(Ticket::start);

    }

}

