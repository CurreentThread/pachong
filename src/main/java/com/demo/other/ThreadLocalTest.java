package com.demo.other;

import com.demo.modal.constant.SystemEnum;

public class ThreadLocalTest {


    static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>() {

        @Override
        protected Object initialValue() {


            System.out.println("\"当前线程还没有共享变量的设置\"");
            return null;
        }
    };

    public static void main(String[] args) {


        Thread thread = new Thread(new ThreadA());
        Thread thread1 = new Thread(new ThreadA());
        thread.start();
        thread1.start();

    }


    static class ThreadA implements Runnable {


        @Override
        public void run() {


            for (int i = 0; i < 3; i++) {
                if (ThreadLocalTest.threadLocal.get() == null) {
                    ThreadLocalTest.threadLocal.set(Thread.currentThread().getName());
                    System.out.println("设置name的初始之为" + Thread.currentThread().getName());
                } else {

                    System.out.println("当前线程的name属性值为" + ThreadLocalTest.threadLocal.get());
                }
            }

        }
    }


}

