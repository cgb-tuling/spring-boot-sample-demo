package org.example.config;

import com.baomidou.mybatisplus.extension.handlers.MybatisEnumTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.enums.SexEnum;
import org.example.enums.StatusEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig implements InitializingBean {
    private final SqlSessionFactory sqlSessionFactory;

    public MybatisConfig(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().register(StatusEnum.class, MybatisEnumTypeHandler.class);
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().register(SexEnum.class, MybatisEnumTypeHandler.class);
    }
}
