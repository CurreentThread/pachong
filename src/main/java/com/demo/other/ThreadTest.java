package com.demo.other;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

//从java8开始可以直接使用lambda表达式创建callable对象
public class ThreadTest implements Callable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread t1 = new Thread();


        FutureTask<Integer> task = new FutureTask<Integer>(new ThreadTest());

//        Executors.newSingleThreadExecutor();

        new Thread(task).start();

        task.get();

    }

    @Override
    public Object call() throws Exception {
        System.out.println(33);
        return 2;
    }
}
