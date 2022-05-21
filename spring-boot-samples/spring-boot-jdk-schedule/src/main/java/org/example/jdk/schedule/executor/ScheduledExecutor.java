package org.example.jdk.schedule.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ScheduledExecutor
 * Description: 线程池章节
 * Date: 2018/12/20 15:48 【需求编号】
 *
 * @author Sam Sho
 * @version V1.0.0
 */
public class ScheduledExecutor {

    public static void main(String[] args) {
//        test();
//        test2();
        test3();
    }

    public static void test() {
        // 只有一个线程
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


        // 如 1 秒后输出
        executor.schedule(new Thread(new ScheduledTaskThread()), 1, TimeUnit.SECONDS);
        executor.schedule(new Thread(new ScheduledTaskThread()), 1, TimeUnit.SECONDS);
        executor.schedule(new Thread(new ScheduledTaskThread()), 1, TimeUnit.SECONDS);

    }

    public static void test2() {
        // 启动三个线程
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        executor.schedule(new Thread(new ScheduledTaskThread()), 1, TimeUnit.SECONDS);
        executor.schedule(new Thread(new ScheduledTaskThread()), 1, TimeUnit.SECONDS);
        executor.schedule(new Thread(new ScheduledTaskThread()), 1, TimeUnit.SECONDS);
    }

    public static void test3() {
        // 启动三个线程
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        // 有频率的定时器, 如 1 秒后输出，每隔 2秒再次输出。
        // 也就是将在 initialDelay(1) 后开始执行，然后在initialDelay + period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推。(任务内部消耗的时间无关)
        executor.scheduleAtFixedRate(new Thread(new ScheduledTaskThread()), 1, 2, TimeUnit.SECONDS);


        // 推迟的定时器，如 1 秒后输出，3秒后再次输出。
        // 创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。(任务内部消耗的时间计算在内)
        executor.scheduleWithFixedDelay(new Thread(new ScheduledTaskThread()), 1, 2, TimeUnit.SECONDS);
    }

    static class ScheduledTaskThread implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {

            }
        }
    }
}
