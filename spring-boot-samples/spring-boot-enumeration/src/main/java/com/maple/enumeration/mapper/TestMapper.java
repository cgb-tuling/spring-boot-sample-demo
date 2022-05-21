package com.maple.enumeration.mapper;

import com.maple.enumeration.entity.TestDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 测试表 Mapper 接口 注释部分为mybatis-plus版本
 * </p>
 *
 * @author maple
 * @since 2020-06-21
 */
//@Mapper
//public interface TestMapper extends BaseMapper<com.maple.enumeration.entity.TestDO> {
//
//}

@Mapper
public interface TestMapper {
    /**
     * 根据id获取
     *
     * @param id id
     * @return TestDO
     */
    @Select("select * from test where id = #{id}")
    TestDO getById(Integer id);
}