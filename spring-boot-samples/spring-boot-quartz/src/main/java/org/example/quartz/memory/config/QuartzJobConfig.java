package org.example.quartz.memory.config;

import org.example.quartz.memory.job.DemoJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.Objects;

/**
 * JobDetail、Trigger Bean配置
 */
@Configuration
public class QuartzJobConfig {

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setName("DemoJob");
        jobDetail.setGroup("DemoJob_Group");
        jobDetail.setJobClass(DemoJob.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean().getObject()));
        trigger.setCronExpression("*/10 * * * * ?");
        trigger.setName("DemoJob");
        trigger.setMisfireInstruction(0);

        return trigger;
    }
}