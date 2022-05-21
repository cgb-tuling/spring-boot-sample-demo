package org.example.quartz.memory.config;

import org.example.quartz.memory.job.MyBootJob;
import org.example.quartz.memory.job.MyBootMethodJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.HashMap;

/**
 * ClassName: SchedulerConfig
 * Description:
 * Date: 2018/12/18 16:21 【需求编号】
 *
 * @author Sam Sho
 * @version V1.0.0
 */

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(MyBootJob job) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(job.getClass());
        factoryBean.setRequestsRecovery(true);
        factoryBean.setDurability(true);

        // 额外参数
        factoryBean.setJobDataAsMap(new HashMap<>());

        return factoryBean;
    }


    /**
     * MyBootMethodJob 不需要实现 Job 接口
     *
     * @param job
     * @return
     */
    @Bean
    public MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean(MyBootMethodJob job) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false); // 是否并发
        jobDetail.setName("general-myFirstExerciseJob"); // 任务的名字
        jobDetail.setGroup("general"); // 任务的分组
        jobDetail.setTargetObject(job); // 被执行的对象
        jobDetail.setTargetMethod("exe"); // 被执行的方法
        return jobDetail;
    }

    /**
     * 简单触发器配置
     *
     * @param jobDetailFactoryBean
     * @return
     */
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        SimpleTriggerFactoryBean tigger = new SimpleTriggerFactoryBean();
        tigger.setJobDetail(jobDetailFactoryBean.getObject());
        tigger.setRepeatInterval(1000L);
        tigger.setRepeatCount(5);
        return tigger;
    }


    /**
     * CronTrigger 触发器配置
     *
     * @param myFirstExerciseJobBean
     * @return
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(myFirstExerciseJobBean.getObject());
        tigger.setCronExpression("0/10 * * * * ?");
        return tigger;
    }

    /**
     * 调度器工厂Bean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactory(SimpleTriggerFactoryBean simpleTriggerFactoryBean,
                                                 CronTriggerFactoryBean cronTriggerFactoryBean) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 覆盖已存在的任务，用于集群配置
        bean.setOverwriteExistingJobs(true);
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        bean.setStartupDelay(15);
        // 注册触发器
        bean.setTriggers(simpleTriggerFactoryBean.getObject(), cronTriggerFactoryBean.getObject());
//        bean.setAutoStartup(false);
        return bean;
    }
}
