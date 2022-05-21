package com.maple.enumeration.service;

import com.maple.enumeration.entity.TestDO;

/**
 * <p>
 * 测试表 服务类 注释部分为mybatis-plus版本
 * </p>
 *
 * @author maple
 * @since 2020-06-21
 */
//public interface TestService extends IService<TestDO> {
//
//}

public interface TestService {
    /**
     * 根据id获取
     *
     * @param id id
     * @return TestDO
     */
    TestDO getById(Integer id);
}