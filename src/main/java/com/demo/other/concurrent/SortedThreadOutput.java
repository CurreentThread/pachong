package com.demo.other.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SortedThreadOutput {

    public static void main(String[] args) throws InterruptedException {

        new SortedThreadOutput().poolMethed();

   /*  Thread a = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("我是线程a,我先执行");

            }
        });


        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("我是线程b,即将执行");

                    a.join();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是线程b,我第2个执行");

            }
        });

        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
//                    Thread.sleep(1000);
                    b.join();//仅在线程已启动时才有用



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是线程c,我第三个执行");

            }
        });


        //启动顺序最好不要变
        a.start();
//        Thread.sleep(2000);
        c.start();
//        Thread.sleep(7000);

        b.start();*/


    }


    public void poolMethed() {


        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {


                System.out.println("我是线程a,我先执行");

            }
        });


        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("我是线程b,即将执行");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("我是线程b,我第2个执行");

            }
        });

        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {


                System.out.println("我是线程c,我第三个执行");

            }
        });


        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(a);
        pool.execute(b);
        pool.execute(c);


    }


}
