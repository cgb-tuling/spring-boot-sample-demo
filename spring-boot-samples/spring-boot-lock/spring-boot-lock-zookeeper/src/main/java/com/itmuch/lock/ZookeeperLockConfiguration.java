package com.itmuch.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

/**
 * @author itmuch.com
 */
@Configuration
public class ZookeeperLockConfiguration{
    private String address = "192.168.61.130:2181";
    private final CuratorFramework client;

    public ZookeeperLockConfiguration() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        this.client.start();
    }

//    @Bean
//    public CuratorFramework curatorFramework() {
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        //return new CuratorFrameworkImpl(CuratorFrameworkFactory.builder());
//        return CuratorFrameworkFactory.newClient(address, retryPolicy);
//    }

    @Bean
    public ZookeeperLockRegistry zookeeperLockRegistry() {
        return new ZookeeperLockRegistry(client);
    }

}
