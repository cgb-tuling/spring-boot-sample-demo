package org.example.schedule.springboot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName: Task
 * Description:
 * Date: 2018/12/17 15:20 【需求编号】
 *
 * @author Sam Sho
 * @version V1.0.0
 */
@Component
public class Task {

    @Scheduled(cron = "*/10 * * * * ?")
    public void doTask() {
        System.out.println("~~~ doTask 定时任务被执行 ~~~");
    }

    @Scheduled(fixedDelay = 1000L)
    public void doTaskFixedDelay() {
        System.out.println("~~~ doTaskFixedDelay 定时任务被执行 ~~~");
    }


    /**
     * 手动添加
     */
    public void doTask2() {
        System.out.println("~~~ doTask2 定时任务被执行 ~~~");
    }
}
