package com.study.concurrency;

import java.util.concurrent.TimeUnit;

public class DeadLock {
    private static final Object READ_LOCK = new Object();
    private static final Object WRITE_LOCK = new Object();

    public static void read() {
        System.out.println("try to get read lock");
        synchronized (READ_LOCK) {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("get read lock, try to get write lock");
            synchronized (WRITE_LOCK) {
                System.out.println("get write lock, begin release write lock");
            }
            System.out.println("release write lock, begin release release read lock");
        }
        System.out.println("release read lock");
    }

    public static void write() {
        System.out.println("try to get write lock");
        synchronized (WRITE_LOCK) {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("get write lock, try to get read lock");
            synchronized (READ_LOCK) {
                System.out.println("get write lock, begin release write lock");
            }
            System.out.println("release write lock, begin release release read lock");
        }
        System.out.println("release read lock");
    }


    public static void main(String[] args) {
//        new Thread(DeadLock::read).start();
//        new Thread(DeadLock::write).start();

        final DeadLock deadLock = new DeadLock();
        new Thread(() -> {
            while (true)
            deadLock.read();
        }).start();

        new Thread(() -> {
            while (true)
                deadLock.write();
        }).start();
    }


    /**
     * Found one Java-level deadlock:
     * =============================
     * "Thread-1":
     *   waiting to lock monitor 0x00007f4d8c003828 (object 0x000000078268f718, a java.lang.Object),
     *   which is held by "Thread-0"
     * "Thread-0":
     *   waiting to lock monitor 0x00007f4d8c0060b8 (object 0x000000078268f728, a java.lang.Object),
     *   which is held by "Thread-1"
     *
     * Java stack information for the threads listed above:
     * ===================================================
     * "Thread-1":
     * 	at com.study.concurrency.DeadLock.write(DeadLock.java:36)
     * 	- waiting to lock <0x000000078268f718> (a java.lang.Object)
     * 	- locked <0x000000078268f728> (a java.lang.Object)
     * 	at com.study.concurrency.DeadLock$$Lambda$2/99747242.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     * "Thread-0":
     * 	at com.study.concurrency.DeadLock.read(DeadLock.java:19)
     * 	- waiting to lock <0x000000078268f728> (a java.lang.Object)
     * 	- locked <0x000000078268f718> (a java.lang.Object)
     * 	at com.study.concurrency.DeadLock$$Lambda$1/1129670968.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * Found 1 deadlock.
     */
}
