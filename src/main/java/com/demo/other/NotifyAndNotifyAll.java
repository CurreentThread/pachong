package com.demo.other;

import sun.nio.ch.ThreadPool;

public class NotifyAndNotifyAll {

    private static final Object obj = new Object();

    static class R implements Runnable {
        int i;

        R(int i) {
            this.i = i;
        }

        public void run() {
            try {
                synchronized (obj) {
                    System.out.println("线程->  " + i + " 等待中");
                    obj.wait();
                    System.out.println("线程->  " + i + " 在运行了");
                    Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int n = 0; n < threads.length; n++) {
            threads[n] = new Thread(new R(n));

        }

        for (Thread r : threads) {
            r.start();
        }

        Thread.sleep(5000);
        synchronized (obj) {
            obj.notifyAll();
        }
    }

}
