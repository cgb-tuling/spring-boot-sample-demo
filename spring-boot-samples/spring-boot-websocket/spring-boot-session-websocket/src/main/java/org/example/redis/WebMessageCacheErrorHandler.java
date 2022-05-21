package org.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class WebMessageCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
        WebMessageRedisCacheSupport.setClosed(true);

        log.error("缓存Get异常，error -> [{}]；key -> [{}]", e.getMessage(), key);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
        WebMessageRedisCacheSupport.setClosed(true);

        log.error("缓存Put异常，error -> [{}]；key -> [{}]；value -> [{}]；", e.getMessage(), key, value);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
        WebMessageRedisCacheSupport.setClosed(true);

        log.error("缓存Evict异常：error -> [{}]；key -> [{}]", e.getMessage(), key);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        WebMessageRedisCacheSupport.setClosed(true);

        log.error("缓存Clear异常：error -> [{}]", e.getMessage());
    }
}
