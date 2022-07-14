package org.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.event.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Executor;

/**
 * 监听配置类
 *
 * @author yct
 */
@Configuration
@Slf4j
@SuppressWarnings(value={"unchecked", "rawtypes"})
public class EventListenerManager {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    //@Qualifier("asyncExecutor")
    private Executor executor;

    @EventListener
    public void handleCustomEvent(MyEvent customEvent) {
        //监听 CustomEvent事件
        log.info("监听到CustomEvent事件，消息为：{}, 发布时间：{}", customEvent, customEvent.getTimestamp());
    }

    /**
     * 监听 username为oKong的事件
     */
    @Async("asyncExecutor")
    @EventListener(condition = "#customEvent.user.username == 'oKong'")
    public void handleCustomEventByCondition(MyEvent customEvent) {
        //监听 CustomEvent事件
        log.info("监听到code为'oKong'的CustomEvent事件，消息为：{}, 发布时间：{}", customEvent, customEvent.getTimestamp());
    }

}