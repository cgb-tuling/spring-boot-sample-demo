package org.example.quartz.jdbc.config;

import org.example.quartz.jdbc.job.JdbcJob;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * ClassName: QuartzConfig
 * Description:
 * Date: 2018/12/18 16:52 【需求编号】
 *
 * @author Sam Sho
 * @version V1.0.0
 */
@Configuration
@EnableScheduling
public class QuartzJdbcConfig {

    @Autowired
    private DataSource dataSource;


    /**
     * 配置自定义 JobFactory
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        return new AutowireCapableBeanJobFactory(applicationContext.getAutowireCapableBeanFactory());
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setName("JdbcJob"); // 任务的名字
        jobDetail.setGroup("JdbcJob_group"); // 任务的分组
        jobDetail.setJobClass(JdbcJob.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }

    /**
     * 表达式触发器工厂Bean
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(jobDetailFactoryBean().getObject());
        tigger.setCronExpression("*/10 * * * * ?"); // 什么是否触发，Spring Scheduler Cron表达式
        tigger.setName("JdbcJob_tigger");
        tigger.setMisfireInstruction(0);

        return tigger;
    }

    /**
     * 配置调度，可以由springboot 的配置文件代替
     *
     * @param jobFactory
     * @return
     * @throws Exception
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("JdbcJob_scheduler");
        // QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        factory.setDataSource(dataSource);
        // 全局监听器
        factory.setGlobalJobListeners(null);
        // 参数配置
        factory.setQuartzProperties(quartzProperties());
        factory.setApplicationContextSchedulerContextKey("ctx");
        factory.setStartupDelay(15);

        factory.setTriggers(cronTriggerFactoryBean().getObject());
        return factory;
    }

    /**
     * Quartz 配置文件
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * ClassName: AutowireCapableBeanJobFactory
     * Description: Quartz Job 中支持 autowiring
     * Date: 2018/9/6 16:45 【需求编号】
     *
     * @author Sam Sho
     * @version V1.0.0
     */
    class AutowireCapableBeanJobFactory extends SpringBeanJobFactory {

        private final AutowireCapableBeanFactory beanFactory;

        AutowireCapableBeanJobFactory(AutowireCapableBeanFactory beanFactory) {
            Assert.notNull(beanFactory, "Bean factory must not be null");
            this.beanFactory = beanFactory;
        }

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object jobInstance = super.createJobInstance(bundle);
            this.beanFactory.autowireBean(jobInstance);
            this.beanFactory.initializeBean(jobInstance, null);
            return jobInstance;
        }

    }
}