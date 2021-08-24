package com.study.concurrency;

import java.util.concurrent.TimeUnit;

public class TicketRunnable implements Runnable {

    private final short max = 500;
    private short sell = 0;

    @Override
    public void run() {
        while (sell < max) {
            sell += 1;
            System.out.println(Thread.currentThread().getName() + " selling 1 ticket, total sell" + sell);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TicketRunnable runnable = new TicketRunnable();
        for (short i = 0; i < 4; i++) {
            new Thread(runnable).start();
        }
    }
}
