package com.itmuch.lock;


import org.apache.geode.cache.Cache;
import org.apache.geode.distributed.DistributedLockService;
import org.apache.geode.distributed.internal.locks.DLockService;
import org.apache.geode.internal.cache.xmlcache.CacheCreation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.gemfire.util.GemfireLockRegistry;

/**
 * @author itmuch.com
 */
@Configuration
public class GemfireLockConfiguration {


    public GemfireLockConfiguration() {

    }

//    @Bean
//    public CuratorFramework curatorFramework() {
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        //return new CuratorFrameworkImpl(CuratorFrameworkFactory.builder());
//        return CuratorFrameworkFactory.newClient(address, retryPolicy);
//    }

    @Bean
    public GemfireLockRegistry gemfireLockRegistry(Cache cache) {
        return new GemfireLockRegistry(cache);
    }

    @Bean
    public Cache cache() {
        return new CacheCreation();
    }

    private DistributedLockService distributedLockService;

    private DLockService dLockService;


    public boolean locks() {
        return dLockService.lock("xx", 100, 100);
    }
}
