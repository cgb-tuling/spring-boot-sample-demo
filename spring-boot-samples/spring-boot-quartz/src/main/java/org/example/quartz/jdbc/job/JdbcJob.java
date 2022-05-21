package org.example.quartz.jdbc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class JdbcJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("~~ JdbcJob ~~");

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {

        }
    }
}