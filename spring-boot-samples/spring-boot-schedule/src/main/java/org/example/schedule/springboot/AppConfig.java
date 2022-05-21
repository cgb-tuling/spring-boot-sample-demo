package org.example.schedule.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ClassName: AppConfig
 * Description: 自定义扩展 SchedulingConfigurer
 * Date: 2018/12/17 17:15 【需求编号】
 *
 * @author Sam Sho
 * @version V1.0.0
 */
@Configuration
@EnableScheduling
public class AppConfig implements SchedulingConfigurer {


    @Autowired
    Task task;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());

        // 手动添加 Trigger
        taskRegistrar.addFixedDelayTask(() -> task.doTask2(), 5000L);
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }


}
