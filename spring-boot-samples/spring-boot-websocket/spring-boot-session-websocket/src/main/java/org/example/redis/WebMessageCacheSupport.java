package org.example.redis;

import java.util.Set;

/**
 * MSA平台提供的缓存操作接口
 */
public interface WebMessageCacheSupport {

    /**
     * 删除缓存
     *
     * @param key 缓存Key
     */
    Boolean delete(String key);

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    void set(String key, Object value, long time);

    /**
     * 清除所有的key
     */
    void clear();

    /**
     * 健康检查
     */
    void healthCheck();

    /**
     * 缓存是否有效
     * @return true：有效；false：无效
     */
    boolean isValid();

    /**
     * 模糊查询所有key
     * @return key集合
     */
    Set<String> getKeysLike(String keyLike);
}
