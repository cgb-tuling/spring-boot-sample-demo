package com.itmuch.lock;


import org.apache.geode.cache.Cache;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.distributed.DistributedLockService;
import org.apache.geode.distributed.internal.locks.DLockService;
import org.apache.geode.internal.cache.xmlcache.CacheCreation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.integration.gemfire.store.GemfireMessageStore;
import org.springframework.integration.gemfire.util.GemfireLockRegistry;
import org.springframework.retry.RetryPolicy;

import java.util.Properties;

/**
 * @author itmuch.com
 */
@Configuration
public class GemfireLockConfiguration {


    public GemfireLockConfiguration() {

    }

    //@Bean
    //public CuratorFramework curatorFramework() {
    //    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    //    //return new CuratorFrameworkImpl(CuratorFrameworkFactory.builder());
    //    return CuratorFrameworkFactory.newClient(address, retryPolicy);
    //}

    @Bean
    public GemfireLockRegistry gemfireLockRegistry(GemFireCache gemFireCache) {
        return new GemfireLockRegistry((Cache) gemFireCache);
    }

    @Bean
    public CacheFactoryBean cacheFactoryBean() {
        return new CacheFactoryBean();
    }

    //@Bean
    //public Cache cache() {
    //    return new CacheCreation();
    //}

    private DLockService dLockService= (DLockService) DistributedLockService.getServiceNamed("GemfireLockService");


    public boolean locks() {
        return dLockService.lock("xx", 100, 100);
    }
}
