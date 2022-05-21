package com.example.data.test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws Exception {

        /*创建CountDownLatch实例,计数器的值初始化为3*/
        final CountDownLatch downLatch = new CountDownLatch(3);

        /*创建三个线程,每个线程等待1s,表示执行比较耗时的任务*/
        for (int i = 0; i < 3; i++) {
            final int num = i;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }

                    System.out.println(String.format("thread %d has finished", num));

                    /*任务完成后调用CountDownLatch的countDown()方法*/
                    downLatch.countDown();
                }
            }).start();
        }

        /*主线程调用await()方法,等到其他三个线程执行完后才继续执行*/
        downLatch.await();

        System.out.print("all threads have finished,main thread will continue run");
    }
}
