package com.maple.enumeration.config;

import com.baomidou.mybatisplus.extension.handlers.MybatisEnumTypeHandler;
import com.maple.enumeration.enums.StatusEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-21 22:11
 */
@Configuration
public class MybatisConfig implements InitializingBean {
    private final SqlSessionFactory sqlSessionFactory;

    public MybatisConfig(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().register(StatusEnum.class, MybatisEnumTypeHandler.class);
    }
}