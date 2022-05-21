package org.example.quartz.memory.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * ClassName: MyBootJob
 * Description:
 * Date: 2018/12/18 16:24 【需求编号】
 *
 * @author Sam Sho
 * @version V1.0.0
 */
@Component
public class MyBootJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("~~ MyBootJob ~~");
    }
}