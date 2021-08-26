package com.study.concurrency;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest {

    private final static Object MUTEX = new Object();

    public void accessResource() {
        synchronized (MUTEX) {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {
        final SynchronizedTest test = new SynchronizedTest();
        for (int i = 0; i < 3; i++) {
            new Thread(test::accessResource).start();
        }
    }
}
