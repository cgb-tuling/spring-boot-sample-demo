package org.example.jdk.schedule.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ClassName: JdkTimer
 * Description:
 * Date: 2018/12/20 15:27 【需求编号】
 *
 * @author Sam Sho
 * @version V1.0.0
 */
public class JdkTimer {

    /**
     * 1、单线程执行，一个任务等待，全部等待
     * 2、一个任务异常，全部挂掉
     * 3、依赖系统时间
     *
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        // 延迟 1s 执行，按照时间间隔 2s 重复执行
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("~~~ JdkTimer ~~~");
            }
        }, 1000L, 2000L);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("~~~ JdkTimer2 ~~~");
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 一个任务异常，全部异常
                int i = 1 / 0;
            }
        }, 2000L, 5000L);
    }
}
