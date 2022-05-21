package org.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * MSA缓存服务类
 */
@Service
public class WebMessageCacheService {

    @Autowired
    private WebMessageCacheSupport webMessageCacheSupport;

    @Autowired
    private CacheErrorHandler errorHandler;

    /**
     * 删除缓存
     *
     * @param key 缓存Key
     */
    public Boolean delete(String key) {
        try {
            return webMessageCacheSupport.delete(key);
        } catch (RuntimeException e) {
            errorHandler.handleCacheEvictError(e, null, key);
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try {
            return webMessageCacheSupport.get(key);
        } catch (RuntimeException e) {
            errorHandler.handleCacheGetError(e, null, key);

            return null;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        try {
            webMessageCacheSupport.set(key, value);
        } catch (RuntimeException e) {
            errorHandler.handleCachePutError(e, null, key, value);
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        try {
            webMessageCacheSupport.set(key, value, time);
        } catch (RuntimeException e) {
            errorHandler.handleCachePutError(e, null, key, value);
        }
    }

    /**
     * 清除库里所有的Key
     */
    public void clear() {
        try {
            webMessageCacheSupport.clear();
        } catch (RuntimeException e) {
            errorHandler.handleCacheClearError(e, null);
        }
    }

    /**
     * 缓存体系是否有效
     */
    public boolean isValid() {
        return webMessageCacheSupport.isValid();
    }

    public Set<String> getKeysLike(String keyLike){
        return webMessageCacheSupport.getKeysLike(keyLike);
    }
}
