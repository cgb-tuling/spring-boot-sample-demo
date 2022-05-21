package org.example.quartz.memory.config;

import org.example.quartz.memory.listener.CustomGlobalJobListener;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerFactoryBeanCustomizerConfig implements SchedulerFactoryBeanCustomizer {

    @Bean
    public CustomGlobalJobListener globalJobListener() {
        return new CustomGlobalJobListener();
    }


    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setGlobalJobListeners(globalJobListener());
    }
}
