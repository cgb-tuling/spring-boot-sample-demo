package org.example.quartz.memory.engine;

import org.example.quartz.memory.model.BatchSchedule;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

public class QuartzScheduleEngine {


    @Autowired
    private Scheduler scheduler;


    /**
     * 新增计划任务: 主要是添加 jobDetail 和 trigger
     *
     * @param batchSchedule
     */
    public Date addJob(BatchSchedule batchSchedule) throws Exception {

        String cronExpression = batchSchedule.getCronExpression();
        String name = batchSchedule.getCode();
        String group = batchSchedule.getTaskCode();
        String interfaceName = batchSchedule.getInterfaceName();

        // 校验数据
        this.checkNotNull(batchSchedule);

        // 添加 1-JobDetail
        // 校验 JobDetail 是否存在
        JobKey jobKey = JobKey.jobKey(name, group);
        if (scheduler.checkExists(jobKey)) {
            if (!StringUtils.hasText(cronExpression)) {
                // 已经存在并且执行一次，立即执行
                scheduler.triggerJob(jobKey);
            } else {
                throw new Exception("任务计划 JobKey 已经在执行队列中，不需要重复启动");
            }
        } else {

            // 构建 JobDetail
            Class<? extends Job> jobClazz = (Class<? extends Job>) Class.forName(interfaceName);
            JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(jobKey).build();
            jobDetail.getJobDataMap().put(BatchSchedule.SCHEDULE_KEY, batchSchedule.toString());

            // 添加 2-Trigger
            // 校验 Trigger 是否存在
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (Objects.nonNull(trigger)) {
                throw new Exception("任务计划 Trigger 已经在执行队列中，不需要重复启动");
            }

            // 构建 Trigger
            trigger = getTrigger(cronExpression, triggerKey);

            return scheduler.scheduleJob(jobDetail, trigger);
        }

        return new Date();
    }


    /**
     * 修改
     *
     * @param batchSchedule
     */
    public void updateCronExpression(BatchSchedule batchSchedule) throws Exception {
        updateJobCronExpression(batchSchedule);
    }


    /**
     * 更新Job的执行表达式
     *
     * @param batchSchedule
     * @throws SchedulerException
     */
    public Date updateJobCronExpression(BatchSchedule batchSchedule) throws SchedulerException {
        checkNotNull(batchSchedule);

        String name = batchSchedule.getCode();
        String group = batchSchedule.getTaskCode();
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
        // 在队列中才需要修改
        if (scheduler.checkExists(triggerKey)) {
            // 构建 Trigger
            String cronExpression = batchSchedule.getCronExpression();
            Trigger trigger = this.getTrigger(cronExpression, triggerKey);
            return scheduler.rescheduleJob(triggerKey, trigger);
        }
        return null;
    }

    /**
     * 构建 Trigger
     *
     * @param cronExpression
     * @param triggerKey
     * @return
     */
    private Trigger getTrigger(String cronExpression, TriggerKey triggerKey) {
        Trigger trigger;
        if (!StringUtils.hasText(cronExpression.trim())) {
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).build();
        } else {
            cronExpression = cronExpression.replaceAll("#", " ");
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
        }
        return trigger;
    }

    /**
     * 暂停计划任务
     *
     * @param batchSchedule
     */
    public void pauseJob(BatchSchedule batchSchedule) throws Exception {
        checkNotNull(batchSchedule);
        JobKey jobKey = JobKey.jobKey(batchSchedule.getCode(), batchSchedule.getTaskCode());
        if (!scheduler.checkExists(jobKey)) {
            throw new Exception("任务计划不在执行队列中，不能暂停");
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 从暂停中恢复
     *
     * @param batchSchedule
     */
    public void resumeJob(BatchSchedule batchSchedule) throws Exception {
        checkNotNull(batchSchedule);
        JobKey jobKey = JobKey.jobKey(batchSchedule.getCode(), batchSchedule.getTaskCode());
        if (!scheduler.checkExists(jobKey)) {
            throw new Exception("任务计划不在执行队列中，不能恢复");
        }

        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除计划任务
     *
     * @param batchSchedule
     */
    public boolean deleteJob(BatchSchedule batchSchedule) throws SchedulerException {
        boolean flag = true;
        checkNotNull(batchSchedule);
        JobKey jobKey = JobKey.jobKey(batchSchedule.getCode(), batchSchedule.getTaskCode());
        if (scheduler.checkExists(jobKey)) {
            flag = scheduler.deleteJob(jobKey);
        }

        return flag;
    }

    /**
     * 添加任务监听
     *
     * @param jobListener
     * @param matcher
     * @throws SchedulerException
     */
    public void addJobListener(JobListener jobListener, Matcher<JobKey> matcher) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(jobListener, matcher);
    }

    /**
     * 执行一次（可用于测试）
     *
     * @param batchSchedule
     */
    public void runJobOnce(BatchSchedule batchSchedule) throws SchedulerException {
        checkNotNull(batchSchedule);
        JobKey jobKey = JobKey.jobKey(batchSchedule.getCode(), batchSchedule.getTaskCode());
        scheduler.triggerJob(jobKey);
    }

    private void checkNotNull(BatchSchedule batchSchedule) {
        Assert.notNull(batchSchedule, "计划为空");
        Assert.hasText(batchSchedule.getCode(), "计划编号为空");
        Assert.hasText(batchSchedule.getTaskCode(), "计划所属任务为空");
        Assert.hasText(batchSchedule.getInterfaceName(), "任务执行业务类为空");
    }


    public SchedulerMetaData getMetaData() throws SchedulerException {
        SchedulerMetaData metaData = scheduler.getMetaData();
        return metaData;
    }
}

