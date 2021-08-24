package com.study.concurrency;

import java.util.concurrent.TimeUnit;

/**
 New
 Runnable  Running   Terminated
 Blocked
**/
public class Concurrency {
    private static short sleepTime = 1;

    public static void main(String[] args) {
        new Thread(()->task1()).start();
        task2();
    }

    private static void task1() {
        while (true) {
            System.out.println("in task1,,,");
            sleep(sleepTime);
        }
    }

    private static void task2() {
        while (true) {
            System.out.println("in task2,,,");
            sleep(sleepTime);
        }
    }

    private static void sleep(short sleepTime) {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
