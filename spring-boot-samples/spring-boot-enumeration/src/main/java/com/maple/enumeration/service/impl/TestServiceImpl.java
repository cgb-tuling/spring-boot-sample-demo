package com.maple.enumeration.service.impl;

import com.maple.enumeration.entity.TestDO;
import com.maple.enumeration.mapper.TestMapper;
import com.maple.enumeration.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试表 服务实现类 注释部分为mybatis-plus版本
 * </p>
 *
 * @author maple
 * @since 2020-06-21
 */
//@Service
//public class TestServiceImpl extends ServiceImpl<TestMapper, TestDO> implements TestService {
//
//}

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public TestDO getById(Integer id) {
        return testMapper.getById(id);
    }
}