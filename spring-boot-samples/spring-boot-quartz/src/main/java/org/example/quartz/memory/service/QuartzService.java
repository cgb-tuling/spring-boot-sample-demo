package org.example.quartz.memory.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Description: Quartz定时任务
 * User: yuanct
 * Date: 2019/1/8 6:47 PM
 */
@Slf4j
public class QuartzService extends QuartzJobBean {

    /**
     * 执行定时任务
     *
     * @param jobExecutionContext
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("{} - quartz task.", System.currentTimeMillis());
    }
}